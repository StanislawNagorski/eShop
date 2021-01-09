package com.ecommerce.eshop.order.service;

import com.ecommerce.eshop.order.exceptions.OrderCreationException;
import com.ecommerce.eshop.order.models.CustomerOrder;
import com.ecommerce.eshop.product.models.Product;
import com.ecommerce.eshop.product.service.ProductService;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class CustomerOrderServiceTest {

    @Autowired
    OrderService orderService;
    @Autowired
    ProductService productService;

    final Long EXISTING_PRODUCT_ID = 7L;
    final Long TEST_ORDER_ID = 1L;
    Product product;

    @Before
    void setUp(){
        product = productService.getById(EXISTING_PRODUCT_ID);
    }

    @After
    void cleanUp(){
        //TODO delete test entry
    }

    @Ignore //TODO unIgnore afret implementing delete
    @Test
    void shouldSaveOrderToDBandReturnItById() {
        //Given
        CustomerOrder customerOrder = new CustomerOrder();
        customerOrder.setProducts(List.of(product));
        //When
        orderService.save(customerOrder);
        //Then
        assertTrue(orderService.getByID(TEST_ORDER_ID).isPresent());
    }

    @Test()
    void shouldTrowExceptionIfOrderAlreadyExists() {
        //Given
        CustomerOrder testCustomerOrder = new CustomerOrder();
        //When
        testCustomerOrder.setProducts(Collections.emptyList());
        //Then
        assertThrows(OrderCreationException.class, () -> orderService.save(testCustomerOrder));
    }
    @Test()
    void shouldTrowExceptionWhenSavingOrderWithoutAnyProducts() {
        //Given
        CustomerOrder testCustomerOrder = new CustomerOrder();
        //When
        testCustomerOrder.setProducts(Collections.emptyList());
        //Then
        assertThrows(OrderCreationException.class, () -> orderService.save(testCustomerOrder));
    }
    @Test
    void shouldSetStatusOfOrderToCreatedWhenSaved(){

    }

    @Test
    void shouldReturnOrderById() {
    }

    @Test
    void shouldReturnAllOrders() {
    }

    @Test
    void shouldReturnListOfAllOrdersThatIncludesProduct() {
    }

    @Test
    void shouldReturnListOfAllOrdersSortedByNewest() {

    }

    @Test
    void shouldReturnListOfOrdersSortedByTotalAmount() {

    }

    @Test
    void shouldReturnListOfOrdersSortedByTotalQuantity() {

    }

    @Test
    void shouldReturnListOfOrderBeforeGivenDate() {

    }

    @Test
    void shouldReturnListOfOrderAfterGivenDate() {

    }

    @Test
    void shouldReturnListOfOrderBetweenGivenDates() {

    }

    @Test
    void shouldReturnListOfOrdersByGivenStatus() {

    }
}
