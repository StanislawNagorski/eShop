package com.ecommerce.eshop.service;

import com.ecommerce.eshop.models.product.Product;
import com.ecommerce.eshop.repositories.ProductRepository;
import com.ecommerce.eshop.utils.exepctions.ProductCreationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public Product save(Product product){
        if (product.getId() != null &&  productRepository.existsById(product.getId())){
           throw new ProductCreationException("Cannot save product with id: " + product.getId() + " it already exists");
        }
        product.setActive(true);
        return productRepository.save(product);
    }

    public Product getById(Long id){
        Optional<Product> byId = productRepository.findById(id);
        if (byId.isEmpty()){
            throw new ProductCreationException("Cannot find product with id: " + id);
        }
        return byId.get();
    }

    public List<Product> getAll(){
        return productRepository.findAll();
    }

    public List<Product> getAllByCategory(String category){
        return productRepository.findAllByCategory_Name(category);
    }

    public List<Product> getAllPromoProducts(){
        return productRepository.findAllByisPromo(true);
    }

    public Product update(Product product){
        Optional<Product> byId = productRepository.findById(product.getId());

        if (byId.isEmpty()){
            throw new ProductCreationException("Cannot update product with id: " + product.getId() + " it dont exists");
        }
        Product productFromDB = byId.get();
        productFromDB.setName(product.getName());
        productFromDB.setDescription(product.getDescription());
        productFromDB.setCategory(product.getCategory());
        productFromDB.setPrice(product.getPrice());
        productFromDB.setProductImages(product.getProductImages());
        productFromDB.setPromo(product.isPromo());
        productFromDB.setPromoPrice(product.getPromoPrice());
        productFromDB.setQuantity(product.getQuantity());
        productFromDB.setActive(product.isActive());

        return productRepository.save(productFromDB);
    }

    public void deactivate(Long id){
        Optional<Product> byId = productRepository.findById(id);

        if (byId.isEmpty()){
            throw new ProductCreationException("Cannot remove product with id: " + id);
        }
        Product productFromDB = byId.get();
        productFromDB.setActive(false);
        productRepository.save(productFromDB);
    }



}
