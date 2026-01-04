package GroceryStoreManagementSystem;

public class GoldCustomer extends Customer {

    private double cashbackRate;

    public GoldCustomer(int customerId, String name, double totalPurchases, double cashbackRate) {
        super(customerId, name, "Gold", totalPurchases); // super must be first
        this.cashbackRate = cashbackRate;
    }

    public double getCashbackRate() {
        return cashbackRate;
    }

    public void setCashbackRate(double cashbackRate) {
        if (cashbackRate >= 0) {
            this.cashbackRate = cashbackRate;
        }
    }


    @Override
    public double calculateDiscountRate() {
        return 0.08; // 8% discount
    }


    @Override
    public String getCustomerType() {
        return "GoldCustomer";
    }


    public double calculateCashback(double billAmount) {
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
