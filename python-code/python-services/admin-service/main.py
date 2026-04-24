import os
from typing import Any, Dict

from fastapi import FastAPI, Depends, Query

from database import products_collection
from auth import verify_token

app = FastAPI(title="Admin Dashboard Service (Mongo Only)")

LOW_STOCK_THRESHOLD = int(os.getenv("LOW_STOCK_THRESHOLD", "5"))
TOP_LIMIT = int(os.getenv("TOP_LIMIT", "10"))

def _serialize_product(doc: Dict[str, Any]) -> Dict[str, Any]:
    return {
        "id": str(doc.get("_id", "")),
        "name": doc.get("name"),
        "category": doc.get("category"),
        "price": float(doc.get("price", 0) or 0),
        "stock": int(doc.get("stock", 0) or 0),
        "views": int(doc.get("views", 0) or 0),
        "purchases": int(doc.get("purchases", 0) or 0),
    }

@app.get("/health")
def health():
    return {"status": "running"}

@app.get("/admin/dashboard/stats")
def stats():
    total_products = products_collection.count_documents({})
    low_stock = products_collection.count_documents({"stock": {"$gt": 0, "$lt": LOW_STOCK_THRESHOLD}})
    out_of_stock = products_collection.count_documents({"stock": {"$lte": 0}})
    return {
        "products": total_products,
        "low_stock": low_stock,
        "out_of_stock": out_of_stock,
    }

@app.get("/admin/dashboard/low-stock")
def low_stock():
    cursor = products_collection.find({"stock": {"$gt": 0, "$lt": LOW_STOCK_THRESHOLD}}).sort("stock", 1)
    return [_serialize_product(doc) for doc in cursor]

@app.get("/admin/dashboard/out-of-stock")
def out_of_stock():
    cursor = products_collection.find({"stock": {"$lte": 0}}).sort("name", 1)
    return [_serialize_product(doc) for doc in cursor]

@app.get("/admin/dashboard/products/by-category")
def products_by_category():
    pipeline = [
        {"$group": {"_id": "$category", "count": {"$sum": 1}}},
        {"$sort": {"count": -1}}
    ]
    rows = list(products_collection.aggregate(pipeline))
    return [{"category": row.get("_id"), "count": row.get("count", 0)} for row in rows]

@app.get("/admin/dashboard/top-viewed")
def top_viewed():
    cursor = products_collection.find({}).sort("views", -1).limit(TOP_LIMIT)
    return [_serialize_product(doc) for doc in cursor]

@app.get("/admin/dashboard/top-purchased")
def top_purchased():
    cursor = products_collection.find({}).sort("purchases", -1).limit(TOP_LIMIT)
    return [_serialize_product(doc) for doc in cursor]

@app.get("/admin/dashboard/inventory-value")
def inventory_value():
    pipeline = [
        {
            "$project": {
                "inventory_value": {
                    "$multiply": [
                        {"$ifNull": ["$price", 0]},
                        {"$ifNull": ["$stock", 0]}
                    ]
                }
            }
        },
        {"$group": {"_id": None, "total_inventory_value": {"$sum": "$inventory_value"}}}
    ]
    result = list(products_collection.aggregate(pipeline))
    total = result[0]["total_inventory_value"] if result else 0
    return {"total_inventory_value": float(total)}

@app.get("/admin/dashboard/products/search")
def search_products(q: str = Query(...), ):
    cursor = products_collection.find({"name": {"$regex": q, "$options": "i"}}).sort("name", 1)
    return [_serialize_product(doc) for doc in cursor]
