package br.com.upper.product.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.upper.product.api.modules.Supplier;

public interface SupplierRepository extends JpaRepository<Supplier, Integer> {

}
