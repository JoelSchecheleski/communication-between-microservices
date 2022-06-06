package br.com.upper.product.api.modules.product.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.upper.product.api.modules.product.dto.ProductResponse;
import br.com.upper.product.api.modules.product.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

     List<Product> findByNameContainingIgnoreCase(String name); // Product

    List<Product> findBySupplierId(Integer supplierId);

    List<Product> findByCategoryId(Integer categoryId);
}
