package GroceryStoreManagementSystem;

public class Main {
    public static void main(String[] args) {

        System.out.println("=== Grocery Store Management System ===\n");

        Product p1 = new Product(1, "Milk", 500, 10);
        Product p2 = new Product(2, "Bread", 200, 0);

        Customer c1 = new Customer(101, "Ali", "Regular", 8000);
        Customer c2 = new Customer(102, "Aruzhan", "Gold", 15000);

        Sale s1 = new Sale(1001, "Ali", 0, "21.12.2025");

        System.out.println("--- PRODUCTS ---");
        System.out.println(p1);
        System.out.println(p2);

        System.out.println("\n--- CUSTOMERS ---");
        System.out.println(c1);
        System.out.println(c2);

        System.out.println("\n--- SALE ---");
        s1.addItem(p1.getPrice());
        s1.addItem(p1.getPrice());
        System.out.println(s1);

        System.out.println("\nVIP customer: " + c2.isVIP());
        System.out.println("Milk in stock: " + p1.isInStock());

        System.out.println("\n=== Program Complete ===");
    }
}
