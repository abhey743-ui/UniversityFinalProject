import os
from fastapi import FastAPI, Depends
from sqlalchemy.orm import Session

from database import SessionLocal
from models import Product
from engine import hot_score

app = FastAPI(title="Recommendation Service")

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

@app.get("/recommend/hot")
def hot(db: Session = Depends(get_db)):
    items = db.query(Product).all()
    ranked = sorted(items, key=hot_score, reverse=True)
    return ranked[:TOP_LIMIT]

@app.get("/recommend/similar/{category}")
def similar(category: str, db: Session = Depends(get_db)):
    return (
        db.query(Product)
        .filter(Product.category == category)
        .limit(TOP_LIMIT)
        .all()
    )

@app.get("/recommend/best-deals")
def best_deals(db: Session = Depends(get_db)):
    return (
        db.query(Product)
        .filter(Product.stock > 0)
        .order_by(Product.price.asc())
        .limit(TOP_LIMIT)
        .all()
    )
