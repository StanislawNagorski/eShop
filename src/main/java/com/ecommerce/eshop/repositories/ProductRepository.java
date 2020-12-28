package com.ecommerce.eshop.repositories;

import com.ecommerce.eshop.models.Product;
import com.ecommerce.eshop.models.ProductCategories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

     List<Product> findAllByCategory(ProductCategories category);
}
