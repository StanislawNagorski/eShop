package com.ecommerce.eshop.order.service;

import com.ecommerce.eshop.order.exceptions.OrderCreationException;
import com.ecommerce.eshop.order.exceptions.OrderNotFoundException;
import com.ecommerce.eshop.order.models.CustomerOrder;
import com.ecommerce.eshop.order.models.OrderStatus;
import com.ecommerce.eshop.order.repositories.OrderRepository;
import com.ecommerce.eshop.product.exceptions.ProductNotFoundException;
import com.ecommerce.eshop.product.models.Product;
import com.ecommerce.eshop.product.repositories.ProductRepository;
import com.ecommerce.eshop.utils.ControllersUtils.DateRange;
import com.ecommerce.eshop.utils.excepctions.ExceptionUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.ecommerce.eshop.utils.excepctions.ExceptionUtils.*;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    public CustomerOrder save(CustomerOrder customerOrder) {
        if (customerOrder.getId() != null && orderRepository.existsById(customerOrder.getId())) {
            throw new OrderCreationException(String.format(ORDER_CANNOT_SAVE, customerOrder.getId()));
        }

        List<Product> orderProducts = customerOrder.getProducts();
        if (orderProducts.isEmpty()) {
            throw new OrderCreationException(ORDER_WITHOUT_ANY_PRODUCTS);
        }
        customerOrder.setProducts(getProductsFromDB(orderProducts));

        customerOrder.setTotalAmount(countTotalAmount(customerOrder));
        customerOrder.setTotalQuantity(countTotalQuantity(customerOrder));

        customerOrder.setOrderStatus(OrderStatus.CREATED);
        return orderRepository.save(customerOrder);
    }

    private List<Product> getProductsFromDB(List<Product> inputProductList){
        List<Product> productListFromDB = new ArrayList<>();
        inputProductList.forEach(product -> {
            Optional<Product> productFromDB = productRepository.findById(product.getId());
            if (productFromDB.isEmpty()){
                throw new ProductNotFoundException(String.format(PRODUCT_CANNOT_FIND, product.getId()));
            }
            productListFromDB.add(productFromDB.get());
        });
        return productListFromDB;
    }

    //TODO update!!

    private BigDecimal countTotalAmount(CustomerOrder customerOrder) {
        BigDecimal totalAmount = BigDecimal.ZERO;
        for (Product product : customerOrder.getProducts()) {
            totalAmount = totalAmount.add(product.getPrice());
        }
        return totalAmount;
    }

    private Integer countTotalQuantity(CustomerOrder customerOrder) {
        return customerOrder.getProducts().size();
    }

    public CustomerOrder getByID(Long id) {
        Optional<CustomerOrder> orderOptional = orderRepository.findById(id);
        if (orderOptional.isEmpty()) {
            throw new OrderNotFoundException(String.format(ORDER_NOT_FOUND, id));
        }
        return orderOptional.get();
    }

    public List<CustomerOrder> getAll() {
        return orderRepository.findAll();
    }

    public List<CustomerOrder> getAllThatIncludesProduct(Long id) {
        Optional<Product> productOptional = productRepository.findById(id);
        if (productOptional.isEmpty()) {
            throw new OrderNotFoundException(String.format(ORDER_CONTAINING_PRODUCT_NOT_FOUND, id));
        }

        return orderRepository.findAllByProductsIsContaining(productOptional.get());
    }

    public List<CustomerOrder> getAllByNewest() {
        return orderRepository.findAllByOrderByCreationTimeDesc();
    }

    public List<CustomerOrder> getAllBetweenDates(DateRange dateRange) {
        return orderRepository.findAllByCreationTimeAfterAndCreationTimeBefore(
                dateRange.getDateTimeFrom(), dateRange.getDateTimeTo());
    }

    public List<CustomerOrder> getAllByTotalAmountDesc() {
        return orderRepository.findAllByOrderByTotalAmountDesc();
    }

    public List<CustomerOrder> getAllByTotalQuantityDesc() {
        return orderRepository.findAllByOrderByTotalQuantityDesc();
    }

    public List<CustomerOrder> getAllByStatus(String statusString) {
        Optional<OrderStatus> optionalOrderStatus = Arrays.stream(OrderStatus.values())
                .filter(orderStatus -> orderStatus.name().equals(statusString))
                .findAny();
        if (optionalOrderStatus.isEmpty()) {
            throw new OrderNotFoundException(String.format(ExceptionUtils.ORDER_STATUS_NOT_FOUND, statusString));
        }
        OrderStatus orderStatus = OrderStatus.valueOf(statusString);
        return orderRepository.findAllByOrderStatus(orderStatus);
    }

    public CustomerOrder deleteById(Long id) {
        Optional<CustomerOrder> orderFromDB = orderRepository.findById(id);
        if (orderFromDB.isEmpty()) {
            throw new OrderNotFoundException(String.format(ORDER_NOT_FOUND, id));
        }
        orderRepository.deleteById(id);
        return orderFromDB.get();
    }


}
