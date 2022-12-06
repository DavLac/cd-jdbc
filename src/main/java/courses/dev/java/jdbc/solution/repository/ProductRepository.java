package courses.dev.java.jdbc.solution.repository;

import courses.dev.java.jdbc.solution.model.Product;
import courses.dev.java.jdbc.solution.model.User;

public interface ProductRepository {
    User createProduct(Product product);
    void deleteProductById(Integer id);
}
