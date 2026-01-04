package GroceryStoreManagementSystem;

public class Customer {


    protected int customerId;
    protected String name;
    protected String membershipLevel;
    protected double totalPurchases;

    public Customer(int customerId, String name, String membershipLevel, double totalPurchases) {
        this.customerId = customerId;
        this.name = name;
        this.membershipLevel = membershipLevel;
        this.totalPurchases = totalPurchases;
    }

    public int getCustomerId() { return customerId; }
    public void setCustomerId(int customerId) { this.customerId = customerId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getMembershipLevel() { return membershipLevel; }
    public void setMembershipLevel(String membershipLevel) { this.membershipLevel = membershipLevel; }

    public double getTotalPurchases() { return totalPurchases; }
    public void setTotalPurchases(double totalPurchases) { this.totalPurchases = totalPurchases; }

    public boolean isVIP() {
        return totalPurchases > 10000;
    }

    public void addPurchase(double amount) {
        if (amount <= 0) {
            System.out.println("Amount must be positive.");
            return;
        }
        totalPurchases += amount;
    }

    public double calculateDiscountRate() {
        return 0.0; // base customer has no discount
    }

    public double calculateFinalPrice(double billAmount) {
        if (billAmount < 0) return billAmount;
        return billAmount - (billAmount * calculateDiscountRate());
    }

    public String getCustomerType() {
        return "Customer";
    }

    @Override
    public String toString() {
        return "[" + getCustomerType() + "] Customer{id=" + customerId +
                ", name='" + name + '\'' +
                ", level='" + membershipLevel + '\'' +
                ", totalPurchases=" + totalPurchases + '}';
    }
}
