
package GroceryStoreManagementSystem.repository;

import GroceryStoreManagementSystem.model.Product;
import java.util.List;

    public interface IProductRepository {
        boolean create(Product p);
        List<Product> getAll();
        Product getById(int id);
        boolean update(int id, Product p);
        boolean delete(int id);
        boolean existsByName(String name);
    }

