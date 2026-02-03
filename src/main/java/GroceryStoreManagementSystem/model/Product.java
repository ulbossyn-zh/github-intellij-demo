package GroceryStoreManagementSystem.model;

import GroceryStoreManagementSystem.exception.InvalidInputException;

public class Product {

    private int productId;
    private String name;
    private double price;
    private int stockQuantity;

    // ===== MAIN CONSTRUCTOR (with ID) =====
    public Product(int productId, String name, double price, int stockQuantity)
            throws InvalidInputException {

        if (productId <= 0)
            throw new InvalidInputException("Product ID must be positive: " + productId);
        if (name == null || name.isBlank())
            throw new InvalidInputException("Product name cannot be empty");
        if (price <= 0)
            throw new InvalidInputException("Price must be positive");
        if (stockQuantity < 0)
            throw new InvalidInputException("Stock quantity cannot be negative");

        this.productId = productId;
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }

    // ===== DB CONSTRUCTOR (NO ID) ✅ =====
    // DB-де product_id = SERIAL → ID-ны PostgreSQL өзі қояды
    public Product(String name, double price, int stockQuantity)
            throws InvalidInputException {

        // id=1 тек validation-нан өту үшін
        this(1, name, price, stockQuantity);
    }

    // ===== GETTERS =====
    public int getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    // ===== SETTERS (optional) =====
    public void setName(String name) throws InvalidInputException {
        if (name == null || name.isBlank())
            throw new InvalidInputException("Product name cannot be empty");
        this.name = name;
    }

    public void setPrice(double price) throws InvalidInputException {
        if (price <= 0)
            throw new InvalidInputException("Price must be positive");
        this.price = price;
    }

    public void setStockQuantity(int stockQuantity) throws InvalidInputException {
        if (stockQuantity < 0)
            throw new InvalidInputException("Stock quantity cannot be negative");
        this.stockQuantity = stockQuantity;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + productId +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", stock=" + stockQuantity +
                '}';
    }
}
