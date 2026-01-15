package GroceryStoreManagementSystem.model;

import GroceryStoreManagementSystem.exception.InvalidInputException;

public class Product {

    private int productId;
    private String name;
    private double price;
    private int stockQuantity;

    public Product(int productId, String name, double price, int stockQuantity) throws InvalidInputException {
        setProductId(productId);
        setName(name);
        setPrice(price);
        setStockQuantity(stockQuantity);
    }

    public int getProductId() { return productId; }
    public String getName() { return name; }
    public double getPrice() { return price; }
    public int getStockQuantity() { return stockQuantity; }

    public void setProductId(int productId) throws InvalidInputException {
        if (productId <= 0) throw new InvalidInputException("Product ID must be positive: " + productId);
        this.productId = productId;
    }

    public void setName(String name) throws InvalidInputException {
        if (name == null || name.trim().isEmpty()) throw new InvalidInputException("Product name cannot be empty");
        this.name = name.trim();
    }

    public void setPrice(double price) throws InvalidInputException {
        if (price < 0) throw new InvalidInputException("Price cannot be negative: " + price);
        this.price = price;
    }

    public void setStockQuantity(int stockQuantity) throws InvalidInputException {
        if (stockQuantity < 0) throw new InvalidInputException("Stock cannot be negative: " + stockQuantity);
        this.stockQuantity = stockQuantity;
    }

    public boolean isInStock() {
        return stockQuantity > 0;
    }

    public void restock(int amount) throws InvalidInputException {
        if (amount <= 0) throw new InvalidInputException("Restock amount must be positive: " + amount);
        stockQuantity += amount;
    }

    @Override
    public String toString() {
        return "Product{id=" + productId +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", stock=" + stockQuantity + '}';
    }
}
