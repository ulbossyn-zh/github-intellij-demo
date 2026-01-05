package GroceryStoreManagementSystem;

public class RegularCustomer extends Customer {

    private int visits;

    public RegularCustomer(int customerId, String name, double totalPurchases, int visits) {
        super(customerId, name, "Regular", totalPurchases); // super must be first
        this.visits = visits;
    }

    public int getVisits() {
        return visits;
    }

    public void setVisits(int visits) {
        if (visits >= 0) {
            this.visits = visits;
        }
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