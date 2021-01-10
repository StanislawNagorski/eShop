package com.ecommerce.eshop.product.repositories;

import com.ecommerce.eshop.product.models.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<ProductCategory, Long> {

    Optional<ProductCategory> getFirstByNameLike(String name);
    Optional<ProductCategory> getById(Long id);


}
