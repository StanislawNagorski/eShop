package com.ecommerce.eshop.service;

import com.ecommerce.eshop.models.product.Product;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class ProductServiceTest {

    @Autowired
    ProductService productService;


    @Test
    void save() {
        //Given
        Product testProduct = new Product();
        testProduct.setName("TestName");
        testProduct.setDescription("TestDescription");
        testProduct.setPrice(BigDecimal.valueOf(16.7));
        testProduct.setPromo(true);
        testProduct.setPromoPrice(BigDecimal.valueOf(12.00));
        //When
        Product savedToDB = productService.save(testProduct);
        //Then
        assertEquals(testProduct, savedToDB);
    }

    @Test
    void getById() {
        //Given
        Long idToTest = 1L;
        //When
        Product productFromDB = productService.getById(idToTest);
        //Then
        assertNotNull(productFromDB);
    }

    @Test
    void getAll() {
        //Given

        //When
        List<Product> products = productService.getAll();
        //Then
        assertFalse(products.isEmpty());
    }

    @Test
    void getAllByCategory() {
        //Given
        String expectedCategoryName = "testcategory";
        //When
        List<Product> allByCategory = productService.getAllByCategory(expectedCategoryName);
        boolean allProductsAreSameCategory = true;
        for (Product product : allByCategory) {
            if (!(product.getCategory().getName().equals(expectedCategoryName))) {
                allProductsAreSameCategory = false;
                break;
            }
        }
        //Then
        assertTrue(allProductsAreSameCategory);
    }

    @Test
    void getAllPromoProducts() {
        //Given

        //When
        List<Product> allPromoProducts = productService.getAllPromoProducts();
        boolean allProductsArePromo = true;
        for (Product product : allPromoProducts) {
            if (!product.isPromo()) {
                allProductsArePromo = false;
                break;
            }
        }
        //Then
        assertTrue(allProductsArePromo);
    }

    @Test
    void getAllProductsByPriceAsc() {
        //Given

        //When
        List<Product> allProductsByPriceAsc = productService.getAllProductsByPriceAsc();
        BigDecimal previousPrice = BigDecimal.ZERO;
        boolean everyNextPriceIsHigher = true;
        for (Product product : allProductsByPriceAsc) {
            BigDecimal currentPrice = product.getPrice();
            if (product.isPromo()){
                currentPrice = product.getPromoPrice();
            }

            if ((currentPrice.compareTo(previousPrice)) <= 0) {
                everyNextPriceIsHigher = false;
                break;
            }
            previousPrice = currentPrice;
        }

        //Then
        assertTrue(everyNextPriceIsHigher);
    }


    @Test
    void deactivate() {
        //Given
        Long idToTest = 1L;
        //When
        productService.deactivate(idToTest);
        Product productFromDB = productService.getById(idToTest);
        //Then
        assertFalse(productFromDB.isActive());
    }
}
