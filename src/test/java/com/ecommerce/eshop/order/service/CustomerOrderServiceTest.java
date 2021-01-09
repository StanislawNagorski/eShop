package com.ecommerce.eshop.order.service;

import com.ecommerce.eshop.order.exceptions.OrderCreationException;
import com.ecommerce.eshop.order.models.CustomerOrder;
import com.ecommerce.eshop.order.models.OrderStatus;
import com.ecommerce.eshop.product.models.Product;
import com.ecommerce.eshop.product.service.ProductService;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
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
    //TODO: jak napisać test żeby nie były zależne od obecnego stanu DB? co pózniej jeśli będzie wersja produkcyjna
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
        product2 = productService.getById(EXISTING_PRODUCT_ID + 1);
        CustomerOrder customerOrder = new CustomerOrder();
        customerOrder.setProducts(List.of(product, product2));
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
    void shouldSetStatusOfOrderToCreatedWhenSaved() {
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
        product2 = productService.getById(EXISTING_PRODUCT_ID + 1);

        CustomerOrder customerOrder = new CustomerOrder();
        customerOrder.setProducts(List.of(product, product2));
        orderService.save(customerOrder);

        CustomerOrder customerOrder1 = new CustomerOrder();
        customerOrder1.setProducts(List.of(product));
        orderService.save(customerOrder1);
        //When
        List<CustomerOrder> allThatIncludesProduct = orderService.getAllThatIncludesProduct(product);
        boolean allOrdersContainsProduct = true;
        for (CustomerOrder order : allThatIncludesProduct) {
            if (!order.getProducts().contains(product)) {
                allOrdersContainsProduct = false;
                break;
            }
        }
        //Then
        assertTrue(allOrdersContainsProduct);
    }

    @Test
    @Transactional
    void shouldReturnListOfAllOrdersSortedByNewest() {
        //Given
        List<CustomerOrder> allByNewest = orderService.getAllByNewest();
        //When
        boolean everyNextOrderIsOlder = true;
        for (int i = 1; i < allByNewest.size(); i++) {
            System.out.println("data stworzenia to i-1 : " + allByNewest.get(i - 1).getCreationTime());
            System.out.println("data stworzenia to i : " + allByNewest.get(i).getCreationTime());
            if (allByNewest.get(i).getCreationTime().isAfter(allByNewest.get(i - 1).getCreationTime())) {
                everyNextOrderIsOlder = false;
                break;
            }
        }
        //Then
        assertTrue(everyNextOrderIsOlder);
    }

    @Test
    void shouldReturnListOfOrderBetweenGivenDates() {
        //Given
        LocalDateTime secondEntryTime = LocalDateTime.of(2021,1,9,15,30);
        LocalDateTime now = LocalDateTime.now();
        List<CustomerOrder> allBetweenDates = orderService.getAllBetweenDates(secondEntryTime, now);
        //When
        boolean allOrdersAreBetweenDates = true;
        for (CustomerOrder order : allBetweenDates) {
            if (order.getCreationTime().isBefore(secondEntryTime)){
                allOrdersAreBetweenDates = false;
                break;
            }
        }
        //Then
        assertTrue(allOrdersAreBetweenDates);
    }

    @Test
    void shouldReturnListOfOrdersSortedByTotalAmount() {
        //Given
        List<CustomerOrder> allByTotalAmountDescending = orderService.getAllByTotalAmountDesc();
        //When
        boolean everyNextOrderIsTotalAmountIsLess = true;
        for (int i = 1; i < allByTotalAmountDescending.size(); i++) {
            BigDecimal previousTotalAmount = allByTotalAmountDescending.get(i-1).getTotalAmount();
            BigDecimal currentTotalAmount = allByTotalAmountDescending.get(i).getTotalAmount();

            System.out.println("previous value: " + previousTotalAmount + "current value: " + currentTotalAmount);

            if (previousTotalAmount.compareTo(currentTotalAmount) < 0) {
                everyNextOrderIsTotalAmountIsLess = false;
                break;
            }
        }
        //Then
        assertTrue(everyNextOrderIsTotalAmountIsLess);
    }

    @Test
    void shouldReturnListOfOrdersSortedByTotalQuantity() {
        //Given
        List<CustomerOrder> allByTotalQuantityDesc = orderService.getAllByTotalQuantityDesc();
        //When
        boolean everyNextOrderTotalQuantityIsLess = true;
        for (int i = 1; i < allByTotalQuantityDesc.size(); i++) {
            Integer previousTotalQuantity = allByTotalQuantityDesc.get(i-1).getTotalQuantity();
            Integer currentTotalQuantity = allByTotalQuantityDesc.get(i).getTotalQuantity();

            System.out.println("previous value: " + previousTotalQuantity + "current value: " + currentTotalQuantity);

            if (previousTotalQuantity < currentTotalQuantity) {
                everyNextOrderTotalQuantityIsLess = false;
                break;
            }
        }
        //Then
        assertTrue(everyNextOrderTotalQuantityIsLess);
    }

    @Test
    void shouldReturnListOfOrdersByGivenStatus() {
        //Given
        OrderStatus expectedStatus = OrderStatus.CREATED;
        List<CustomerOrder> allByStatus = orderService.getAllByStatus(expectedStatus);
        //When
        boolean allAreSameStatus = true;
        for (CustomerOrder byStatus : allByStatus) {
            if (byStatus.getOrderStatus() != expectedStatus) {
                allAreSameStatus = false;
                break;
            }
        }
        //Then
        assertTrue(allAreSameStatus);
    }


    @Ignore
    @Test
    void shouldReturnTrueIfDeleteIsSuccessful() {
        //Given

        //When
        orderService.deleteById(TEST_ORDER_ID);
        //Then
        assertTrue(orderService.getByID(TEST_ORDER_ID).isEmpty());
    }
}
