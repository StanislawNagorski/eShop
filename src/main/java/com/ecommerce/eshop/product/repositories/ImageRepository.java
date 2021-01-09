package com.ecommerce.eshop.product.repositories;

import com.ecommerce.eshop.product.models.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<ProductImage, Long> {

}
