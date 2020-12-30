package com.ecommerce.eshop.service;

import com.ecommerce.eshop.models.product.ProductCategory;
import com.ecommerce.eshop.repositories.CategoryRepository;
import com.ecommerce.eshop.utils.exepctions.CategoryCreationException;
import com.ecommerce.eshop.utils.exepctions.CategoryNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.ecommerce.eshop.utils.exepctions.ExceptionUtils.*;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public ProductCategory save(String productCategoryName){
        String lowerCaseCategoryName = productCategoryName.toLowerCase();
        if (getCategoryNames().contains(lowerCaseCategoryName)){
            throw new CategoryCreationException(
                    String.format(CATEGORY_CANNOT_SAVE_DUPLICATE, lowerCaseCategoryName));
        }
        ProductCategory productCategory = new ProductCategory();
        productCategory.setName(lowerCaseCategoryName);

        return categoryRepository.save(productCategory);
    }

    public ProductCategory getOneByName(String categoryName){
        Optional<ProductCategory> firstByName = categoryRepository.getFirstByNameLike(categoryName);
        if (firstByName.isEmpty()){
            throw new CategoryNotFoundException(String.format(CATEGORY_NOT_FOUND_BY_NAME, categoryName));
        }
        return firstByName.get();
    }

    public ProductCategory getById(Long id){
        Optional<ProductCategory> byId = categoryRepository.getById(id);
        if (byId.isEmpty()){
            throw new CategoryNotFoundException(String.format(CATEGORY_NOT_FOUND_BY_ID,id));
        }
        return byId.get();
    }

    public List<String> getCategoryNames(){
       return categoryRepository.findAll()
               .stream()
               .map(ProductCategory::getName)
               .collect(Collectors.toList());
    }

}
