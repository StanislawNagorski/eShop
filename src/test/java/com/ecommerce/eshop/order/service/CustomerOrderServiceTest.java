package com.ecommerce.eshop.order.service;

import com.ecommerce.eshop.order.exceptions.OrderCreationException;
import com.ecommerce.eshop.order.models.CustomerOrder;
import com.ecommerce.eshop.order.models.OrderStatus;
import com.ecommerce.eshop.product.models.Product;
import com.ecommerce.eshop.product.service.ProductService;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
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
    //TODO: jak napisać test żeby nie zmieniać tego ręcznie? Wyciągać z listy i brac ostatni?
    final Long TEST_ORDER_ID = 2L;
    Product product;
    Product product2;

    @Before
    void setUp() {
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

    @Ignore //TODO unIgnore after implementing delete
    @Test
    void shouldSaveOrderToDBandReturnItById() {
        //Given
        product = productService.getById(EXISTING_PRODUCT_ID);
        product2 = productService.getById(EXISTING_PRODUCT_ID+1);
        CustomerOrder customerOrder = new CustomerOrder();
        customerOrder.setProducts(List.of(product,product2));
        //When
        orderService.save(customerOrder);
        //Then
      //  assertTrue(orderService.getByID(TEST_ORDER_ID).isPresent());
    }
    @Test()
    void shouldTrowExceptionIfOrderAlreadyExists() {
        //Given
        CustomerOrder testCustomerOrder = new CustomerOrder();
        //When
        testCustomerOrder.setId(TEST_ORDER_ID);
        //Then
        assertThrows(OrderCreationException.class, () -> orderService.save(testCustomerOrder));
    }
    @Test
    void shouldSetStatusOfOrderToCreatedWhenSaved(){
        assertSame(orderService.getByID(TEST_ORDER_ID).get().getOrderStatus(), OrderStatus.CREATED);
    }

    @Test
    void shouldReturnAllOrders() {
        assertFalse(orderService.getAll().isEmpty());
    }

    @Test
    @Transactional
    void shouldReturnListOfAllOrdersThatIncludesProduct() {
        //Given
        product = productService.getById(EXISTING_PRODUCT_ID);
        product2 = productService.getById(EXISTING_PRODUCT_ID+1);

        CustomerOrder customerOrder = new CustomerOrder();
        customerOrder.setProducts(List.of(product,product2));
        orderService.save(customerOrder);

        CustomerOrder customerOrder1 = new CustomerOrder();
        customerOrder1.setProducts(List.of(product));
        orderService.save(customerOrder1);
        //When
        List<CustomerOrder> allThatIncludesProduct = orderService.getAllThatIncludesProduct(product);
        boolean allOrdersContainsProduct = true;
        for (CustomerOrder order : allThatIncludesProduct) {
            if (!order.getProducts().contains(product)){
                allOrdersContainsProduct = false;
                break;
            }
        }
        //Then
        assertTrue(allOrdersContainsProduct);
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

    @Ignore
    @Test
    void shouldReturnTrueIfDeleteIsSuccessful(){
        //Given

        //When
        orderService.deleteById(TEST_ORDER_ID);
        //Then
        assertTrue(orderService.getByID(TEST_ORDER_ID).isEmpty());
    }
}
