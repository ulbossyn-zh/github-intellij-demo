package GroceryStoreManagementSystem.model;

public interface Discountable {
    double calculateDiscountRate();
    double calculateFinalPrice(double billAmount);
}
