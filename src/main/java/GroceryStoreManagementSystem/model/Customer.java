package GroceryStoreManagementSystem.model;

import GroceryStoreManagementSystem.exception.InvalidInputException;

public abstract class Customer implements Discountable {

    protected int customerId;
    protected String name;
    protected String membershipLevel;
    protected double totalPurchases;

    public Customer(int customerId, String name, String membershipLevel, double totalPurchases)
            throws InvalidInputException {
        setCustomerId(customerId);
        setName(name);
        setMembershipLevel(membershipLevel);
        setTotalPurchases(totalPurchases);
    }

    public abstract String getCustomerType();

    public int getCustomerId() { return customerId; }
    public String getName() { return name; }
    public String getMembershipLevel() { return membershipLevel; }
    public double getTotalPurchases() { return totalPurchases; }

    public void setCustomerId(int customerId) throws InvalidInputException {
        if (customerId <= 0) throw new InvalidInputException("Customer ID must be positive: " + customerId);
        this.customerId = customerId;
    }

    public void setName(String name) throws InvalidInputException {
        if (name == null || name.trim().isEmpty()) throw new InvalidInputException("Name cannot be empty");
        this.name = name.trim();
    }

    public void setMembershipLevel(String membershipLevel) throws InvalidInputException {
        if (membershipLevel == null || membershipLevel.trim().isEmpty())
            throw new InvalidInputException("Membership level cannot be empty");
        this.membershipLevel = membershipLevel.trim();
    }

    public void setTotalPurchases(double totalPurchases) throws InvalidInputException {
        if (totalPurchases < 0) throw new InvalidInputException("Total purchases cannot be negative: " + totalPurchases);
        this.totalPurchases = totalPurchases;
    }

    public boolean isVIP() {
        return totalPurchases > 10000;
    }

    public void addPurchase(double amount) throws InvalidInputException {
        if (amount <= 0) throw new InvalidInputException("Purchase amount must be positive: " + amount);
        totalPurchases += amount;
    }

    @Override
    public double calculateDiscountRate() {
        return 0.0;
    }

    @Override
    public double calculateFinalPrice(double billAmount) {
        if (billAmount < 0) throw new IllegalArgumentException("Bill amount cannot be negative: " + billAmount);
        return billAmount - (billAmount * calculateDiscountRate());
    }

    @Override
    public String toString() {
        return "[" + getCustomerType() + "] Customer{id=" + customerId +
                ", name='" + name + '\'' +
                ", level='" + membershipLevel + '\'' +
                ", totalPurchases=" + totalPurchases + '}';
    }
}
