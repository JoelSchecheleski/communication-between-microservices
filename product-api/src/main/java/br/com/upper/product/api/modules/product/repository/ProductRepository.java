package br.com.upper.product.api.modules.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.upper.product.api.modules.product.model.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {

}
