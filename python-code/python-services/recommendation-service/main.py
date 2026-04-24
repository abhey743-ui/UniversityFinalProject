import os
from fastapi import FastAPI

from database import products_collection
from engine import hot_score

app = FastAPI(title="Recommendation Service (Mongo Only)")

TOP_LIMIT = int(os.getenv("TOP_LIMIT", "10"))

def _serialize_product(doc):
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

@app.get("/recommend/hot")
def hot():
    items = [_serialize_product(doc) for doc in products_collection.find({})]
    ranked = sorted(items, key=hot_score, reverse=True)
    return ranked[:TOP_LIMIT]

@app.get("/recommend/similar/{category}")
def similar(category: str):
    cursor = products_collection.find({"category": category}).limit(TOP_LIMIT)
    return [_serialize_product(doc) for doc in cursor]

@app.get("/recommend/best-deals")
def best_deals():
    cursor = products_collection.find({"stock": {"$gt": 0}}).sort("price", 1).limit(TOP_LIMIT)
    return [_serialize_product(doc) for doc in cursor]
