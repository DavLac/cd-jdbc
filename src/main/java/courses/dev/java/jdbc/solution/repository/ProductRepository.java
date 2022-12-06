package courses.dev.java.jdbc.solution.repository;

import courses.dev.java.jdbc.solution.model.Product;
import courses.dev.java.jdbc.solution.model.User;

public interface ProductRepository {
    public User createProduct(Product product);
    public void deleteProductById(Integer id);
}
