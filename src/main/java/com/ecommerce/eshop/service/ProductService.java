package com.ecommerce.eshop.service;

import com.ecommerce.eshop.models.product.Product;
import com.ecommerce.eshop.models.product.ProductCategory;
import com.ecommerce.eshop.repositories.ProductRepository;
import com.ecommerce.eshop.utils.exepctions.ProductCreationException;
import com.ecommerce.eshop.utils.exepctions.ProductNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryService categoryService;

    public Product save(Product product){
        if (product.getId() != null &&  productRepository.existsById(product.getId())){
           throw new ProductCreationException("Cannot save product with id: " + product.getId() + " it already exists");
        }

        if (product.getCategory().getId() == null){
            ProductCategory oneByName = categoryService.getOneByName(product.getCategory().getName());
            product.setCategory(oneByName);
        }

        product.setActive(true);
        return productRepository.save(product);
    }

    public Product getById(Long id){
        Optional<Product> byId = productRepository.findById(id);
        if (byId.isEmpty()){
            throw new ProductNotFoundException("Cannot find product with id: " + id);
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

    private List<Product> sortByPriceAscendingWithPromoPrice(List<Product> nonSortedList){
        return nonSortedList
                .stream()
                .sorted((o1, o2) -> {
                    if (o1.isPromo() && o2.isPromo()){
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

    public List<Product> getAllProductsByPriceAsc(){
       return sortByPriceAscendingWithPromoPrice(productRepository.findAllByPriceNotNullOrderByPriceAsc());
    }

    public List<Product> getAllProductsByPriceDesc(){
        List<Product> allProductsByPrice = getAllProductsByPriceAsc();
        Collections.reverse(allProductsByPrice);
        return allProductsByPrice;
    }

    public List<Product> getAllByPriceAndCategoryAsc(String category){
        List<Product> resultList = productRepository.findAllByPriceNotNullAndCategory_Name(category);
        return sortByPriceAscendingWithPromoPrice(resultList);
    }

    public List<Product> getAllByPriceAndCategoryDesc(String category){
        List<Product> resultList = sortByPriceAscendingWithPromoPrice(getAllByPriceAndCategoryAsc(category));
        Collections.reverse(resultList);
        return resultList;
    }

    public List<Product> getAllByName(String name) {
        return productRepository.findAllByNameContains(name);
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
