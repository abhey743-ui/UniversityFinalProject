import os
from dotenv import load_dotenv
from pymongo import MongoClient
import certifi

load_dotenv()

MONGO_URI = os.getenv("MONGO_URI")
MONGO_DB_NAME = os.getenv("MONGO_DB_NAME", "Products")
MONGO_COLLECTION_NAME = os.getenv("MONGO_COLLECTION_NAME", "products")

mongo_client = MongoClient(
    MONGO_URI,
    tls=True,
    tlsCAFile=certifi.where(),
)

mongo_db = mongo_client[MONGO_DB_NAME]
products_collection = mongo_db[MONGO_COLLECTION_NAME]