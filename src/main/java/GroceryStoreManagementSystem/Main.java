package GroceryStoreManagementSystem;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    private static final Scanner sc = new Scanner(System.in);

    private static final ArrayList<Customer> customers = new ArrayList<>();
    private static final ArrayList<Product> products = new ArrayList<>();
    private static final ArrayList<Sale> sales = new ArrayList<>();

    public static void main(String[] args) {

        // Seed data (optional)
        products.add(new Product(1, "Milk", 500, 10));
        products.add(new Product(2, "Bread", 200, 0));

        customers.add(new Customer(101, "Ali", "Base", 8000));
        customers.add(new RegularCustomer(102, "Aruzhan", 15000, 12));
        customers.add(new GoldCustomer(103, "Dana", 22000, 3.0));

        while (true) {
            printMenu();
            int choice = readInt("Enter choice: ");

            switch (choice) {
                case 1 -> addBaseCustomer();        // menu requirement
                case 2 -> addRegularCustomer();     // menu requirement
                case 3 -> addGoldCustomer();        // menu requirement
                case 4 -> viewAllCustomers();       // menu requirement
                case 5 -> polymorphismDemo();       // menu requirement
                case 6 -> viewRegularOnly();        // menu requirement (instanceof + downcasting)
                case 7 -> viewGoldOnly();           // menu requirement (instanceof + downcasting)
                case 0 -> {
                    System.out.println("\n=== Program Complete ===");
                    return;
                }
                default -> System.out.println("Invalid choice.");
            }

            System.out.println("\nPress Enter to continue...");
            sc.nextLine();
        }
    }

    private static void printMenu() {
        System.out.println("\n========================================");
        System.out.println("     GROCERY STORE MANAGEMENT SYSTEM");
        System.out.println("========================================");
        System.out.println("1. Add Customer (Base)");
        System.out.println("2. Add RegularCustomer");
        System.out.println("3. Add GoldCustomer");
        System.out.println("4. View All Customers (Polymorphic)");
        System.out.println("5. Polymorphism Demo (Discount)");
        System.out.println("6. View Regular Only (instanceof + downcasting)");
        System.out.println("7. View Gold Only (instanceof + downcasting)");
        System.out.println("0. Exit");
        System.out.println("========================================");
    }

    private static void addBaseCustomer() {
        System.out.println("\n--- ADD Customer (Base) ---");
        int id = readInt("Customer ID: ");
        String name = readLine("Name: ");
        String level = readLine("Membership level (e.g., Base/Silver): ");
        double purchases = readDouble("Total purchases: ");

        customers.add(new Customer(id, name, level, purchases));
        System.out.println("Added ");
    }

    private static void addRegularCustomer() {
        System.out.println("\n--- ADD RegularCustomer ---");
        int id = readInt("Customer ID: ");
        String name = readLine("Name: ");
        double purchases = readDouble("Total purchases: ");
        int visits = readInt("Visits count: ");

        customers.add(new RegularCustomer(id, name, purchases, visits));
        System.out.println("Added ");
    }

    private static void addGoldCustomer() {
        System.out.println("\n--- ADD GoldCustomer ---");
        int id = readInt("Customer ID: ");
        String name = readLine("Name: ");
        double purchases = readDouble("Total purchases: ");
        double cashbackRate = readDouble("Cashback rate (%): ");

        customers.add(new GoldCustomer(id, name, purchases, cashbackRate));
        System.out.println("Added ");
    }

    private static void viewAllCustomers() {
        System.out.println("\n--- ALL CUSTOMERS (POLYMORPHIC) ---");
        if (customers.isEmpty()) {
            System.out.println("No customers found.");
            return;
        }

        for (int i = 0; i < customers.size(); i++) {
            Customer c = customers.get(i);
            System.out.println((i + 1) + ". " + c);

            if (c instanceof RegularCustomer rc) {
                if (rc.isFrequentBuyer()) System.out.println("   Frequent buyer ");
            } else if (c instanceof GoldCustomer gc) {
                if (gc.eligibleForFreeDelivery()) System.out.println("   Free delivery ");
            } else {
                if (c.isVIP()) System.out.println("   VIP ");
            }
        }
    }

    private static void polymorphismDemo() {
        System.out.println("\n--- POLYMORPHISM DEMO (Discount Calculation) ---");
        if (customers.isEmpty()) {
            System.out.println("No customers to test.");
            return;
        }

        double bill = readDouble("Enter bill amount: ");

        for (Customer c : customers) {
            double rate = c.calculateDiscountRate();
            double finalPrice = c.calculateFinalPrice(bill);

            System.out.println(c.getCustomerType()
                    + " | " + c.getName()
                    + " | discount=" + (rate * 100) + "%"
                    + " | final=" + finalPrice);
        }

        System.out.println(" Same method, different behavior = POLYMORPHISM!");
    }

    private static void viewRegularOnly() {
        System.out.println("\n--- REGULAR ONLY ---");
        int count = 0;

        for (Customer c : customers) {
            if (c instanceof RegularCustomer rc) { // instanceof + downcasting
                count++;
                System.out.println(count + ". " + rc);

                rc.addVisit(); // child-specific method
                System.out.println("   Visit added. Now visits=" + rc.getVisits());
            }
        }

        if (count == 0) System.out.println("No RegularCustomer found.");
    }

    private static void viewGoldOnly() {
        System.out.println("\n--- GOLD ONLY ---");
        int count = 0;

        for (Customer c : customers) {
            if (c instanceof GoldCustomer gc) { // instanceof + downcasting
                count++;
                System.out.println(count + ". " + gc);

                double exampleBill = 10000;
                System.out.println("   Example cashback on " + exampleBill + " = " + gc.calculateCashback(exampleBill));
            }
        }

        if (count == 0) System.out.println("No GoldCustomer found.");
    }

    private static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(sc.nextLine().trim());
            } catch (Exception e) {
                System.out.println("Enter a valid integer.");
            }
        }
    }

    private static double readDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Double.parseDouble(sc.nextLine().trim());
            } catch (Exception e) {
                System.out.println("Enter a valid number.");
            }
        }
    }

    private static String readLine(String prompt) {
        System.out.print(prompt);
        return sc.nextLine();
    }
}

