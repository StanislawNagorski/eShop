package com.ecommerce.eshop.service;

import com.ecommerce.eshop.models.product.ProductCategory;
import com.ecommerce.eshop.repositories.CategoryRepository;
import com.ecommerce.eshop.utils.exepctions.CategoryCreationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public ProductCategory save(ProductCategory productCategory){
        if (getCategoryNames().contains(productCategory.getName())){
            throw new CategoryCreationException(
                    String.format("Category named: %s already exists in DB", productCategory.getName()));
        }

        return categoryRepository.save(productCategory);
    }

    public ProductCategory getOneByName(String categoryName){
        return categoryRepository.getFirstByNameLike(categoryName);
    }

    public Set<String> getCategoryNames(){
       return categoryRepository.findAll()
                .stream()
                .map(ProductCategory::getName)
                .collect(Collectors.toSet());
    }

}
