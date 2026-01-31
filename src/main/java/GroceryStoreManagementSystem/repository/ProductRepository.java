package GroceryStoreManagementSystem.repository;

import GroceryStoreManagementSystem.db.DatabaseConnection;
import GroceryStoreManagementSystem.model.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductRepository implements IProductRepository {

    @Override
    public boolean existsByName(String name) {
        String sql = "SELECT 1 FROM products WHERE LOWER(product_name) = LOWER(?)";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setString(1, name);
            try (ResultSet rs = st.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new RuntimeException("DB error existsByName: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean create(Product p) {
        if (existsByName(p.getName())) {
            throw new IllegalArgumentException("Product with same name already exists.");
        }

        String sql = "INSERT INTO products(product_name, price, stock_quantity) VALUES (?, ?, ?)";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setString(1, p.getName());
            st.setDouble(2, p.getPrice());
            st.setInt(3, p.getStockQuantity());

            return st.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("DB error create: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Product> getAll() {
        String sql = "SELECT product_id, product_name, price, stock_quantity FROM products ORDER BY product_id";
        List<Product> list = new ArrayList<>();

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement st = con.prepareStatement(sql);
             ResultSet rs = st.executeQuery()) {

            while (rs.next()) {
                Product p = new Product(
                        rs.getInt("product_id"),
                        rs.getString("product_name"),
                        rs.getDouble("price"),
                        rs.getInt("stock_quantity")
                );
                list.add(p);
            }
            return list;

        } catch (Exception e) {
            throw new RuntimeException("DB error getAll: " + e.getMessage(), e);
        }
    }

    @Override
    public Product getById(int id) {
        String sql = "SELECT product_id, product_name, price, stock_quantity FROM products WHERE product_id = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setInt(1, id);

            try (ResultSet rs = st.executeQuery()) {
                if (!rs.next()) return null;

                return new Product(
                        rs.getInt("product_id"),
                        rs.getString("product_name"),
                        rs.getDouble("price"),
                        rs.getInt("stock_quantity")
                );
            }

        } catch (Exception e) {
            throw new RuntimeException("DB error getById: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean update(int id, Product p) {
        Product cur = getById(id);
        if (cur == null) throw new IllegalArgumentException("Product not found by id.");

        if (!cur.getName().equalsIgnoreCase(p.getName()) && existsByName(p.getName())) {
            throw new IllegalArgumentException("Another product with same name already exists.");
        }

        String sql = "UPDATE products SET product_name = ?, price = ?, stock_quantity = ? WHERE product_id = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setString(1, p.getName());
            st.setDouble(2, p.getPrice());
            st.setInt(3, p.getStockQuantity());
            st.setInt(4, id);

            return st.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("DB error update: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean delete(int id) {
        // Optional: not found check
        if (getById(id) == null) throw new IllegalArgumentException("Product not found by id.");

        String sql = "DELETE FROM products WHERE product_id = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setInt(1, id);
            return st.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("DB error delete: " + e.getMessage(), e);
        }
    }

    // ===================== ✅ WEEK 8 SEARCH =====================

    @Override
    public List<Product> searchByName(String keyword) {
        String sql = """
            SELECT product_id, product_name, price, stock_quantity
            FROM products
            WHERE product_name ILIKE ?
            ORDER BY product_id
        """;

        List<Product> list = new ArrayList<>();
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setString(1, "%" + keyword + "%");

            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    Product p = new Product(
                            rs.getInt("product_id"),
                            rs.getString("product_name"),
                            rs.getDouble("price"),
                            rs.getInt("stock_quantity")
                    );
                    list.add(p);
                }
            }
            return list;

        } catch (Exception e) {
            throw new RuntimeException("DB error searchByName: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Product> searchByPriceRange(double min, double max) {
        String sql = """
            SELECT product_id, product_name, price, stock_quantity
            FROM products
            WHERE price BETWEEN ? AND ?
            ORDER BY price
        """;

        List<Product> list = new ArrayList<>();
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setDouble(1, min);
            st.setDouble(2, max);

            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    Product p = new Product(
                            rs.getInt("product_id"),
                            rs.getString("product_name"),
                            rs.getDouble("price"),
                            rs.getInt("stock_quantity")
                    );
                    list.add(p);
                }
            }
            return list;

        } catch (Exception e) {
            throw new RuntimeException("DB error searchByPriceRange: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Product> searchByMinPrice(double min) {
        String sql = """
            SELECT product_id, product_name, price, stock_quantity
            FROM products
            WHERE price >= ?
            ORDER BY price
        """;

        List<Product> list = new ArrayList<>();
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setDouble(1, min);

            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    Product p = new Product(
                            rs.getInt("product_id"),
                            rs.getString("product_name"),
                            rs.getDouble("price"),
                            rs.getInt("stock_quantity")
                    );
                    list.add(p);
                }
            }
            return list;

        } catch (Exception e) {
            throw new RuntimeException("DB error searchByMinPrice: " + e.getMessage(), e);
        }
    }
}
