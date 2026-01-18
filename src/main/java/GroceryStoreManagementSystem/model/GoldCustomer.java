package GroceryStoreManagementSystem.model;

import GroceryStoreManagementSystem.exception.InvalidInputException;

public class GoldCustomer extends Customer {

    private double cashbackRate;

    public GoldCustomer(int customerId, String name, double totalPurchases, double cashbackRate)
            throws InvalidInputException {
        super(customerId, name, "Gold", totalPurchases);
        setCashbackRate(cashbackRate);
    }

    public double getCashbackRate() { return cashbackRate; }

    public void setCashbackRate(double cashbackRate) throws InvalidInputException {
        if (cashbackRate < 0) throw new InvalidInputException("Cashback rate cannot be negative: " + cashbackRate);
        this.cashbackRate = cashbackRate;
    }

    @Override
    public double calculateDiscountRate() {
        return 0.08;
    }

    @Override
    public String getCustomerType() {
        return "GoldCustomer";
    }

    public double calculateCashback(double billAmount) {
        if (billAmount < 0) throw new IllegalArgumentException("Bill amount cannot be negative: " + billAmount);
        return billAmount * (cashbackRate / 100.0);
    }

    public boolean eligibleForFreeDelivery() {
        return totalPurchases >= 20000;
    }

    @Override
    public String toString() {
        return super.toString() + " | cashbackRate=" + cashbackRate + "%";
    }
}