package com.ecommerce.eshop.repositories;

import com.ecommerce.eshop.models.product.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<ProductCategory, Long> {

    Optional<ProductCategory> getFirstByNameLike(String name);
    Optional<ProductCategory> getById(Long id);
}
