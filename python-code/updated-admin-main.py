import os
from fastapi import FastAPI, Depends
from sqlalchemy import func, desc
from sqlalchemy.orm import Session

from database import SessionLocal
from models import User, Product
from auth import verify_token

app = FastAPI(title="Admin Dashboard Service")

LOW_STOCK_THRESHOLD = int(os.getenv("LOW_STOCK_THRESHOLD", "5"))
TOP_LIMIT = int(os.getenv("TOP_LIMIT", "10"))

def get_db():
    db = SessionLocal()
    try:
        yield db
    finally:
        db.close()

@app.get("/health")
def health():
    return {"status": "running"}

@app.get("/admin/dashboard/stats")
def stats(db: Session = Depends(get_db), token=Depends(verify_token)):
    return {
        "users": db.query(User).count(),
        "products": db.query(Product).count(),
        "low_stock": db.query(Product).filter(Product.stock < LOW_STOCK_THRESHOLD, Product.stock > 0).count(),
        "out_of_stock": db.query(Product).filter(Product.stock <= 0).count(),
    }

@app.get("/admin/dashboard/low-stock")
def low_stock(db: Session = Depends(get_db), token=Depends(verify_token)):
    return (
        db.query(Product)
        .filter(Product.stock < LOW_STOCK_THRESHOLD, Product.stock > 0)
        .order_by(Product.stock.asc())
        .all()
    )

@app.get("/admin/dashboard/out-of-stock")
def out_of_stock(db: Session = Depends(get_db), token=Depends(verify_token)):
    return (
        db.query(Product)
        .filter(Product.stock <= 0)
        .order_by(Product.name.asc())
        .all()
    )

@app.get("/admin/dashboard/products/by-category")
def products_by_category(db: Session = Depends(get_db), token=Depends(verify_token)):
    rows = (
        db.query(Product.category, func.count(Product.id).label("count"))
        .group_by(Product.category)
        .order_by(desc("count"))
        .all()
    )
    return [{"category": row[0], "count": row[1]} for row in rows]

@app.get("/admin/dashboard/top-viewed")
def top_viewed(db: Session = Depends(get_db), token=Depends(verify_token)):
    return (
        db.query(Product)
        .order_by(desc(func.coalesce(Product.views, 0)))
        .limit(TOP_LIMIT)
        .all()
    )

@app.get("/admin/dashboard/top-purchased")
def top_purchased(db: Session = Depends(get_db), token=Depends(verify_token)):
    return (
        db.query(Product)
        .order_by(desc(func.coalesce(Product.purchases, 0)))
        .limit(TOP_LIMIT)
        .all()
    )

@app.get("/admin/dashboard/inventory-value")
def inventory_value(db: Session = Depends(get_db), token=Depends(verify_token)):
    total = db.query(func.coalesce(func.sum(Product.price * Product.stock), 0.0)).scalar()
    return {"total_inventory_value": float(total)}

@app.get("/admin/dashboard/products/search")
def search_products(q: str, db: Session = Depends(get_db), token=Depends(verify_token)):
    return (
        db.query(Product)
        .filter(Product.name.ilike(f"%{q}%"))
        .order_by(Product.name.asc())
        .all()
    )
