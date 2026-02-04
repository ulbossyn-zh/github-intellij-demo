package GroceryStoreManagementSystem.db;

import GroceryStoreManagementSystem.exception.InvalidInputException;
import GroceryStoreManagementSystem.model.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {

    private Product map(ResultSet rs) throws SQLException {
        try {
            return new Product(
                    rs.getInt("product_id"),
                    rs.getString("product_name"),
                    rs.getDouble("price"),
                    rs.getInt("stock_quantity")
            );
        } catch (InvalidInputException e) {
            throw new RuntimeException("Invalid product data from DB: " + e.getMessage(), e);
        }
    }

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
    public boolean create(Product p) {
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

    public List<Product> getAll() {
        String sql = "SELECT product_id, product_name, price, stock_quantity FROM products ORDER BY product_id";
        List<Product> list = new ArrayList<>();

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement st = con.prepareStatement(sql);
             ResultSet rs = st.executeQuery()) {

            while (rs.next()) {
                list.add(map(rs));
            }
            return list;

        } catch (SQLException e) {
            throw new RuntimeException("DB error getAll: " + e.getMessage(), e);
        }
    }

    public Product getById(int id) {
        String sql = "SELECT product_id, product_name, price, stock_quantity FROM products WHERE product_id = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setInt(1, id);

            try (ResultSet rs = st.executeQuery()) {
                if (!rs.next()) return null;
                return map(rs);
            }

        } catch (SQLException e) {
            throw new RuntimeException("DB error getById: " + e.getMessage(), e);
        }
    }

    public boolean update(int id, Product p) {
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

    public boolean delete(int id) {
        String sql = "DELETE FROM products WHERE product_id = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setInt(1, id);
            return st.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("DB error delete: " + e.getMessage(), e);
        }
    }

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
                    list.add(map(rs));
                }
            }
            return list;

        } catch (SQLException e) {
            throw new RuntimeException("DB error searchByName: " + e.getMessage(), e);
        }
    }

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
                    list.add(map(rs));
                }
            }
            return list;

        } catch (SQLException e) {
            throw new RuntimeException("DB error searchByPriceRange: " + e.getMessage(), e);
        }
    }

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
                    list.add(map(rs));
                }
            }
            return list;

        } catch (SQLException e) {
            throw new RuntimeException("DB error searchByMinPrice: " + e.getMessage(), e);
        }
    }
}
