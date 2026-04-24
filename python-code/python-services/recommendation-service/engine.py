def hot_score(product: dict) -> float:
    views = product.get("views", 0) or 0
    purchases = product.get("purchases", 0) or 0
    return (views * 0.4) + (purchases * 0.6)
