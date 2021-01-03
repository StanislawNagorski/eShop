package com.ecommerce.eshop.repositories;

import com.ecommerce.eshop.models.product.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<ProductImage, Long> {

}
