package GroceryStoreManagementSystem.model;

import GroceryStoreManagementSystem.exception.InvalidInputException;

public class RegularCustomer extends Customer {

    private int visits;

    public RegularCustomer(int customerId, String name, double totalPurchases, int visits)
            throws InvalidInputException {
        super(customerId, name, "Regular", totalPurchases);
        setVisits(visits);
    }

    public int getVisits() { return visits; }

    public void setVisits(int visits) throws InvalidInputException {
        if (visits < 0) throw new InvalidInputException("Visits cannot be negative: " + visits);
        this.visits = visits;
    }

    @Override
    public double calculateDiscountRate() {
        return 0.02;
    }

    @Override
    public String getCustomerType() {
        return "RegularCustomer";
    }

    public void addVisit() {
        visits++;
    }

    public boolean isFrequentBuyer() {
        return visits >= 10;
    }

    @Override
    public String toString() {
        return super.toString() + " | visits=" + visits;
    }
}
