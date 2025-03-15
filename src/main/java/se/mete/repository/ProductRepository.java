package se.mete.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.mete.model.Product;
import java.util.List;

/**
 * JpaRepository built-in for CRUD operations on the Product entity.
 * Extends JpaRepository to leverage Spring Data JPA's built-in methods for database operations.
 */
public interface ProductRepository extends JpaRepository<Product, Long> {
    /**
     * Custom query method to find products by their name.
     *
     * @param productName The name of the product to search for
     * @return List of Product objects matching the given product name
     */
    List<Product> findByProductName(String productName);
}
