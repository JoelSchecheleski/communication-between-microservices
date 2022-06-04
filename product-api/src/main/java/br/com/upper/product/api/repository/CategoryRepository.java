package br.com.upper.product.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.upper.product.api.modules.Category;
@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

}
