package se.mete.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.mete.model.Product;
import java.util.List;

/**
 * JpaRepository built-in for CRUD operations
 */
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByProductName(String productName);
}


