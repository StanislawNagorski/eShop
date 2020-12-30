package com.ecommerce.eshop.service;

import com.ecommerce.eshop.models.product.Product;
import com.ecommerce.eshop.models.product.ProductCategory;
import com.ecommerce.eshop.repositories.CategoryRepository;
import com.ecommerce.eshop.repositories.ProductRepository;
import com.ecommerce.eshop.utils.excepctions.ProductCreationException;
import com.ecommerce.eshop.utils.excepctions.ProductNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.ecommerce.eshop.utils.excepctions.ExceptionUtils.*;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryService categoryService;

    public Product save(Product product) {
        if (product.getId() != null && productRepository.existsById(product.getId())) {
            throw new ProductCreationException(String.format(PRODUCT_CANNOT_SAVE, product.getId()));
        }

        if (product.getCategory() != null) {
            ProductCategory categoryFromDB;
            if (product.getCategory().getId() != null) {
                categoryFromDB = categoryService.getById(product.getCategory().getId());
                product.setCategory(categoryFromDB);
            }
            if (product.getCategory().getName() != null) {
                categoryFromDB = categoryService.getOneByName(product.getCategory().getName());
                product.setCategory(categoryFromDB);
            }
        }

        product.setActive(true);
        return productRepository.save(product);
    }


    public Product getById(Long id) {
        Optional<Product> byId = productRepository.findById(id);
        if (byId.isEmpty()) {
            throw new ProductNotFoundException(String.format(PRODUCT_CANNOT_FIND, id));
        }
        return byId.get();
    }

    public List<Product> getAll() {
        return productRepository.findAll();
    }

    public List<Product> getAllByCategory(String category) {
        return productRepository.findAllByCategory_Name(category);
    }

    public List<Product> getAllPromoProducts() {
        return productRepository.findAllByisPromo(true);
    }

    private List<Product> sortByPriceAscendingWithPromoPrice(List<Product> nonSortedList) {
        return nonSortedList
                .stream()
                .sorted((o1, o2) -> {
                    if (o1.isPromo() && o2.isPromo()) {
                        return o1.getPromoPrice().compareTo(o2.getPromoPrice());
                    }

                    if (o1.isPromo()) {
                        return o1.getPromoPrice().compareTo(o2.getPrice());
                    }

                    if (o2.isPromo()) {
                        return o1.getPrice().compareTo(o2.getPromoPrice());
                    }

                    return o1.getPrice().compareTo(o2.getPrice());
                })
                .collect(Collectors.toList());
    }

    public List<Product> getAllProductsByPriceAsc() {
        return sortByPriceAscendingWithPromoPrice(productRepository.findAllByPriceNotNullOrderByPriceAsc());
    }

    public List<Product> getAllProductsByPriceDesc() {
        List<Product> allProductsByPrice = getAllProductsByPriceAsc();
        Collections.reverse(allProductsByPrice);
        return allProductsByPrice;
    }

    public List<Product> getAllByPriceAndCategoryAsc(String category) {
        List<Product> resultList = productRepository.findAllByPriceNotNullAndCategory_Name(category);
        return sortByPriceAscendingWithPromoPrice(resultList);
    }

    public List<Product> getAllByPriceAndCategoryDesc(String category) {
        List<Product> resultList = sortByPriceAscendingWithPromoPrice(getAllByPriceAndCategoryAsc(category));
        Collections.reverse(resultList);
        return resultList;
    }

    public List<Product> getAllByName(String name) {
        return productRepository.findAllByNameContains(name);
    }

    public Product update(Long id, Product product) {
        Optional<Product> byId = productRepository.findById(id);

        if (byId.isEmpty()) {
            throw new ProductCreationException(String.format(PRODUCT_CANNOT_FIND, product.getId()));
        }
        Product productFromDB = byId.get();

        if (product.getName() != null) {
            productFromDB.setName(product.getName());
        }
        if (product.getDescription() != null) {
            productFromDB.setDescription(product.getDescription());
        }
        if (product.getCategory() != null) {
            ProductCategory categoryFromBD;
            if (product.getCategory().getId() != null) {
                categoryFromBD = categoryService.getById(product.getCategory().getId());
                productFromDB.setCategory(categoryFromBD);
            }
            if (product.getCategory().getName() != null) {
                categoryFromBD = categoryService.getOneByName(product.getCategory().getName());
                productFromDB.setCategory(categoryFromBD);
            }
        }
        if (product.getPrice() != null) {
            productFromDB.setPrice(product.getPrice());
        }
        if (product.getProductImages() != null) {
            productFromDB.setProductImages(product.getProductImages());
        }
        if (product.isPromo()) {
            productFromDB.setPromo(product.isPromo());
            productFromDB.setPromoPrice(product.getPromoPrice());
        }

        if (product.getQuantity() != null) {
            productFromDB.setQuantity(product.getQuantity());
        }

        return productRepository.save(productFromDB);
    }

    public Product deactivate(Long id) {
        Optional<Product> byId = productRepository.findById(id);

        if (byId.isEmpty()) {
            throw new ProductNotFoundException(String.format(PRODUCT_CANNOT_FIND, id));
        }
        Product productFromDB = byId.get();

        if (!productFromDB.isActive()) {
            throw new ProductCreationException(String.format(PRODUCT_CANNOT_DEACTIVATE, id));
        }

        productFromDB.setActive(false);
        return productRepository.save(productFromDB);
    }

}
