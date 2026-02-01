package GroceryStoreManagementSystem.menu;

import GroceryStoreManagementSystem.exception.InvalidInputException;
import GroceryStoreManagementSystem.model.*;
import GroceryStoreManagementSystem.repository.IProductRepository;
import GroceryStoreManagementSystem.repository.ProductRepository;

import java.util.ArrayList;
import java.util.Scanner;

public class MenuManager implements Menu {

    private final Scanner sc = new Scanner(System.in);

    private final ArrayList<Customer> customers = new ArrayList<>();
    private final ArrayList<Product> products = new ArrayList<>();
    private final ArrayList<Sale> sales = new ArrayList<>();

    private final IProductRepository productRepo = new ProductRepository();

    public MenuManager() {
        try {
            products.add(new Product(1, "Milk", 500, 10));
            products.add(new Product(2, "Bread", 200, 0));

            customers.add(new BaseCustomer(101, "Ali", "Base", 8000));
            customers.add(new RegularCustomer(102, "Aruzhan", 15000, 12));
            customers.add(new GoldCustomer(103, "Dana", 22000, 3.0));
        } catch (InvalidInputException e) {
            System.out.println("Init error: " + e.getMessage());
        }
    }

    @Override
    public void displayMenu() {
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

        System.out.println("----------------------------------------");
        System.out.println("8. Create Product (DB)");
        System.out.println("9. View All Products (DB)");
        System.out.println("10. View Product By ID (DB)");
        System.out.println("11. Update Product (DB)");
        System.out.println("12. Delete Product (DB) [Safe]");

        System.out.println("13. Search Product by Name (DB)");
        System.out.println("14. Search Product by Price Range (DB)");
        System.out.println("15. Search Product by Min Price (DB)");

        System.out.println("0. Exit");
        System.out.println("========================================");
    }

    @Override
    public void run() {
        while (true) {
            displayMenu();
            System.out.print("Enter choice: ");

            try {
                int choice = Integer.parseInt(sc.nextLine().trim());

                switch (choice) {
                    case 1 -> addBaseCustomer();
                    case 2 -> addRegularCustomer();
                    case 3 -> addGoldCustomer();
                    case 4 -> viewAllCustomers();
                    case 5 -> polymorphismDemo();
                    case 6 -> viewRegularOnly();
                    case 7 -> viewGoldOnly();

                    case 8 -> createProductDB();
                    case 9 -> viewAllProductsDB();
                    case 10 -> viewProductByIdDB();
                    case 11 -> updateProductDB();
                    case 12 -> deleteProductDBSafe();

                    case 13 -> searchProductByNameDB();
                    case 14 -> searchProductByPriceRangeDB();
                    case 15 -> searchProductByMinPriceDB();

                    case 0 -> {
                        System.out.println("\n=== Program Complete ===");
                        return;
                    }
                    default -> System.out.println("Invalid choice.");
                }

                System.out.println("\nPress Enter to continue...");
                sc.nextLine();

            } catch (NumberFormatException e) {
                System.out.println("Error: Please enter a number.");
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Unexpected error: " + e.getMessage());
            }
        }
    }



    private void addBaseCustomer() {
        System.out.println("\n--- ADD Customer (Base) ---");
        try {
            int id = readInt("Customer ID: ");
            String name = readLine("Name: ");
            String level = readLine("Membership level (e.g., Base/Silver): ");
            double purchases = readDouble("Total purchases: ");

            customers.add(new BaseCustomer(id, name, level, purchases));
            System.out.println("Added ✅");

        } catch (InvalidInputException e) {
            System.out.println("❌ " + e.getMessage());
        }
    }

    private void addRegularCustomer() {
        System.out.println("\n--- ADD RegularCustomer ---");
        try {
            int id = readInt("Customer ID: ");
            String name = readLine("Name: ");
            double purchases = readDouble("Total purchases: ");
            int visits = readInt("Visits count: ");

            customers.add(new RegularCustomer(id, name, purchases, visits));
            System.out.println("Added ✅");

        } catch (InvalidInputException e) {
            System.out.println("❌ " + e.getMessage());
        }
    }

    private void addGoldCustomer() {
        System.out.println("\n--- ADD GoldCustomer ---");
        try {
            int id = readInt("Customer ID: ");
            String name = readLine("Name: ");
            double purchases = readDouble("Total purchases: ");
            double cashbackRate = readDouble("Cashback rate (%): ");

            customers.add(new GoldCustomer(id, name, purchases, cashbackRate));
            System.out.println("Added ✅");

        } catch (InvalidInputException e) {
            System.out.println("❌ " + e.getMessage());
        }
    }

    private void viewAllCustomers() {
        System.out.println("\n--- ALL CUSTOMERS (POLYMORPHIC) ---");
        if (customers.isEmpty()) {
            System.out.println("No customers found.");
            return;
        }

        for (int i = 0; i < customers.size(); i++) {
            Customer c = customers.get(i);
            System.out.println((i + 1) + ". " + c);

            if (c instanceof RegularCustomer rc) {
                if (rc.isFrequentBuyer()) System.out.println("   Frequent buyer ✅");
            } else if (c instanceof GoldCustomer gc) {
                if (gc.eligibleForFreeDelivery()) System.out.println("   Free delivery ✅");
            } else {
                if (c.isVIP()) System.out.println("   VIP ✅");
            }
        }
    }

    private void polymorphismDemo() {
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

        System.out.println("Same method, different behavior = POLYMORPHISM ✅");
    }

    private void viewRegularOnly() {
        System.out.println("\n--- REGULAR ONLY ---");
        int count = 0;

        for (Customer c : customers) {
            if (c instanceof RegularCustomer rc) {
                count++;
                System.out.println(count + ". " + rc);

                rc.addVisit();
                System.out.println("   Visit added. Now visits=" + rc.getVisits());
            }
        }
        if (count == 0) System.out.println("No RegularCustomer found.");
    }

    private void viewGoldOnly() {
        System.out.println("\n--- GOLD ONLY ---");
        int count = 0;
        for (Customer c : customers) {
            if (c instanceof GoldCustomer gc) {
                count++;
                System.out.println(count + ". " + gc);
            }
        }
        if (count == 0) System.out.println("No GoldCustomer found.");
    }


    private void createProductDB() {
        System.out.println("\n--- CREATE PRODUCT (DB) ---");
        try {
            String name = readLine("Product name: ");
            double price = readDouble("Price: ");
            int stock = readInt("Stock quantity: ");

            // DB id өзі береді (SERIAL), бірақ сенің Product конструктор id сұрайды — 1 деп береміз
            Product p = new Product(1, name, price, stock);

            boolean ok = productRepo.create(p);
            System.out.println(ok ? "Created ✅" : "Failed ❌");

        } catch (InvalidInputException e) {
            System.out.println("❌ " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("❌ " + e.getMessage());
        }
    }

    private void viewAllProductsDB() {
        System.out.println("\n--- ALL PRODUCTS (DB) ---");
        var list = productRepo.getAll();
        if (list.isEmpty()) System.out.println("No products found.");
        else list.forEach(System.out::println);
    }

    private void viewProductByIdDB() {
        System.out.println("\n--- PRODUCT BY ID (DB) ---");
        int id = readInt("Enter product_id: ");
        Product p = productRepo.getById(id);
        System.out.println(p == null ? "Not found." : p);
    }

    private void updateProductDB() {
        System.out.println("\n--- UPDATE PRODUCT (DB) ---");
        try {
            int id = readInt("Enter product_id to update: ");
            String name = readLine("New name: ");
            double price = readDouble("New price: ");
            int stock = readInt("New stock: ");

            Product updated = new Product(1, name, price, stock);
            boolean ok = productRepo.update(id, updated);
            System.out.println(ok ? "Updated ✅" : "Failed ❌");

        } catch (InvalidInputException e) {
            System.out.println("❌ " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("❌ " + e.getMessage());
        }
    }

    private void deleteProductDBSafe() {
        System.out.println("\n--- DELETE PRODUCT (DB) [SAFE] ---");
        int id = readInt("Enter product_id to delete: ");

        Product p = productRepo.getById(id);
        if (p == null) {
            System.out.println("Not found.");
            return;
        }

        System.out.println("Found: " + p);
        String confirm = readLine("Are you sure? (yes/no): ").toLowerCase();

        if (!confirm.equals("yes")) {
            System.out.println("Cancelled ✅");
            return;
        }

        try {
            boolean ok = productRepo.delete(id);
            System.out.println(ok ? "Deleted ✅" : "Failed ❌");
        } catch (IllegalArgumentException e) {
            System.out.println("❌ " + e.getMessage());
        }
    }


    private void searchProductByNameDB() {
        System.out.println("\n--- SEARCH PRODUCT BY NAME (DB) ---");
        String keyword = readLine("Enter keyword: ");
        var list = productRepo.searchByName(keyword);
        if (list.isEmpty()) System.out.println("No matches.");
        else list.forEach(System.out::println);
    }

    private void searchProductByPriceRangeDB() {
        System.out.println("\n--- SEARCH PRODUCT BY PRICE RANGE (DB) ---");
        double min = readDouble("Min price: ");
        double max = readDouble("Max price: ");
        var list = productRepo.searchByPriceRange(min, max);
        if (list.isEmpty()) System.out.println("No matches.");
        else list.forEach(System.out::println);
    }

    private void searchProductByMinPriceDB() {
        System.out.println("\n--- SEARCH PRODUCT BY MIN PRICE (DB) ---");
        double min = readDouble("Min price: ");
        var list = productRepo.searchByMinPrice(min);
        if (list.isEmpty()) System.out.println("No matches.");
        else list.forEach(System.out::println);
    }


    private int readInt(String prompt) {
        System.out.print(prompt);
        return Integer.parseInt(sc.nextLine().trim());
    }

    private double readDouble(String prompt) {
        System.out.print(prompt);
        return Double.parseDouble(sc.nextLine().trim());
    }

    private String readLine(String prompt) {
        System.out.print(prompt);
        return sc.nextLine().trim();
    }
}

