package GroceryStoreManagementSystem.repository;

import GroceryStoreManagementSystem.db.ProductDAO;
import GroceryStoreManagementSystem.model.Product;

import java.util.List;

public class ProductRepository implements IProductRepository {

    private final ProductDAO dao = new ProductDAO();

    @Override
    public boolean existsByName(String name) {
        return dao.existsByName(name);
    }

    @Override
    public boolean create(Product p) {
        if (dao.existsByName(p.getName())) {
            throw new IllegalArgumentException("Product with same name already exists.");
        }
        return dao.create(p);
    }

    @Override
    public List<Product> getAll() {
        return dao.getAll();
    }

    @Override
    public Product getById(int id) {
        return dao.getById(id);
    }

    @Override
    public boolean update(int id, Product p) {
        Product cur = dao.getById(id);
        if (cur == null) throw new IllegalArgumentException("Product not found by id.");

        if (!cur.getName().equalsIgnoreCase(p.getName()) && dao.existsByName(p.getName())) {
            throw new IllegalArgumentException("Another product with same name already exists.");
        }
        return dao.update(id, p);
    }

    @Override
    public boolean delete(int id) {
        if (dao.getById(id) == null) throw new IllegalArgumentException("Product not found by id.");
        return dao.delete(id);
    }

    // ===== WEEK 8 SEARCH =====
    @Override
    public List<Product> searchByName(String keyword) {
        return dao.searchByName(keyword);
    }

    @Override
    public List<Product> searchByPriceRange(double min, double max) {
        return dao.searchByPriceRange(min, max);
    }

    @Override
    public List<Product> searchByMinPrice(double min) {
        return dao.searchByMinPrice(min);
    }
}
