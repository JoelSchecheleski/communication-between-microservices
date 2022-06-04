package br.com.upper.product.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.upper.product.api.modules.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {

}
