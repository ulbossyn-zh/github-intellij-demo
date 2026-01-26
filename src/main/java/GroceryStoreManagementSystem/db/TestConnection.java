package GroceryStoreManagementSystem.db;

public class TestConnection {
    public static void main(String[] args) {
        try (var con = DatabaseConnection.getConnection()) {
            System.out.println("✅ Connected!");
        } catch (Exception e) {
            System.out.println("❌ Connection failed: " + e.getMessage());
        }
    }
}
