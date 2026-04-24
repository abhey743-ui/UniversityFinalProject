from pydantic import BaseModel

class DashboardStats(BaseModel):
    users: int
    products: int
    low_stock: int
    out_of_stock: int

class CategoryBreakdownItem(BaseModel):
    category: str | None
    count: int

class InventoryValueResponse(BaseModel):
    total_inventory_value: float
