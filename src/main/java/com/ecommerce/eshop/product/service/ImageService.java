package com.ecommerce.eshop.product.service;

import com.ecommerce.eshop.product.models.Product;
import com.ecommerce.eshop.product.models.ProductImage;
import com.ecommerce.eshop.product.repositories.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ImageService {
    private final ImageRepository imageRepository;

    public ProductImage saveImageToProduct(Product product, ProductImage image){
        image.setProductId(product.getId());
        return imageRepository.save(image);
    }
}
