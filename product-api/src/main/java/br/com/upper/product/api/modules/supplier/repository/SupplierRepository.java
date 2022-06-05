package br.com.upper.product.api.modules.supplier.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.upper.product.api.modules.supplier.model.Supplier;
@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Integer> {

}
