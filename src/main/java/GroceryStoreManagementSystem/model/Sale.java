package GroceryStoreManagementSystem.model;

import GroceryStoreManagementSystem.exception.InvalidInputException;

public class Sale {

    private int saleId;
    private String customerName;
    private double totalAmount;
    private String date;

    public Sale(int saleId, String customerName, double totalAmount, String date) throws InvalidInputException {
        setSaleId(saleId);
        setCustomerName(customerName);
        setTotalAmount(totalAmount);
        setDate(date);
    }

    public int getSaleId() { return saleId; }
    public String getCustomerName() { return customerName; }
    public double getTotalAmount() { return totalAmount; }
    public String getDate() { return date; }

    public void setSaleId(int saleId) throws InvalidInputException {
        if (saleId <= 0) throw new InvalidInputException("Sale ID must be positive: " + saleId);
        this.saleId = saleId;
    }

    public void setCustomerName(String customerName) throws InvalidInputException {
        if (customerName == null || customerName.trim().isEmpty())
            throw new InvalidInputException("Customer name cannot be empty");
        this.customerName = customerName.trim();
    }

    public void setTotalAmount(double totalAmount) throws InvalidInputException {
        if (totalAmount < 0) throw new InvalidInputException("Total amount cannot be negative: " + totalAmount);
        this.totalAmount = totalAmount;
    }

    public void setDate(String date) throws InvalidInputException {
        if (date == null || date.trim().isEmpty()) throw new InvalidInputException("Date cannot be empty");
        this.date = date.trim();
    }

    public void addItem(double price) throws InvalidInputException {
        if (price < 0) throw new InvalidInputException("Item price cannot be negative: " + price);
        totalAmount += price;
    }

    public boolean isBigSale() {
        return totalAmount > 5000;
    }

    @Override
    public String toString() {
        return "Sale{id=" + saleId +
                ", customer='" + customerName + '\'' +
                ", total=" + totalAmount +
                ", date='" + date + '\'' + '}';
    }
}
