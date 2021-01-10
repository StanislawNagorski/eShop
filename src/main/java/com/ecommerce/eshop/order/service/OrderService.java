package com.ecommerce.eshop.order.service;

import com.ecommerce.eshop.order.exceptions.OrderCreationException;
import com.ecommerce.eshop.order.exceptions.OrderNotFoundException;
import com.ecommerce.eshop.order.models.CustomerOrder;
import com.ecommerce.eshop.order.models.OrderStatus;
import com.ecommerce.eshop.order.repositories.OrderRepository;
import com.ecommerce.eshop.product.models.Product;
import com.ecommerce.eshop.product.repositories.ProductRepository;
import com.ecommerce.eshop.utils.ControllersUtils.DateRange;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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

        if (customerOrder.getProducts().isEmpty()) {
            throw new OrderCreationException(ORDER_WITHOUT_ANY_PRODUCTS);
        }
        customerOrder.setTotalAmount(countTotalAmount(customerOrder));
        customerOrder.setTotalQuantity(countTotalQuantity(customerOrder));

        customerOrder.setOrderStatus(OrderStatus.CREATED);
        return orderRepository.save(customerOrder);
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

    public List<CustomerOrder> getAllByStatus(OrderStatus orderStatus) {
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
