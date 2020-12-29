package com.ecommerce.eshop.repositories;

import com.ecommerce.eshop.models.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

     List<Product> findAllByCategory_Name(String category_name);
     List<Product> findAllByCategory_Id(Long category_id);
}
