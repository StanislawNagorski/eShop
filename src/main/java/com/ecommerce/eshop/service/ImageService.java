package com.ecommerce.eshop.service;

import com.ecommerce.eshop.models.product.Product;
import com.ecommerce.eshop.models.product.ProductImage;
import com.ecommerce.eshop.repositories.ImageRepository;
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
