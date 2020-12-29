package com.ecommerce.eshop.service;

import com.ecommerce.eshop.models.product.ProductCategory;
import com.ecommerce.eshop.repositories.CategoryRepository;
import com.ecommerce.eshop.utils.exepctions.CategoryCreationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public ProductCategory save(String productCategoryName){
        String lowerCaseCategoryName = productCategoryName.toLowerCase();
        if (getCategoryNames().contains(lowerCaseCategoryName)){
            throw new CategoryCreationException(
                    String.format("Category named: %s already exists in DB", lowerCaseCategoryName));
        }
        ProductCategory productCategory = new ProductCategory();
        productCategory.setName(lowerCaseCategoryName);

        return categoryRepository.save(productCategory);
    }

    public ProductCategory getOneByName(String categoryName){
        return categoryRepository.getFirstByNameLike(categoryName);
    }

    public List<String> getCategoryNames(){
       return categoryRepository.findAll()
               .stream()
               .map(ProductCategory::getName)
               .collect(Collectors.toList());
    }

}
