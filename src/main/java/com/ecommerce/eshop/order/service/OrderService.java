package com.ecommerce.eshop.order.service;

import com.ecommerce.eshop.order.exceptions.OrderCreationException;
import com.ecommerce.eshop.order.exceptions.OrderNotFoundException;
import com.ecommerce.eshop.order.models.CustomerOrder;
import com.ecommerce.eshop.order.models.OrderStatus;
import com.ecommerce.eshop.order.repositories.OrderRepository;
import com.ecommerce.eshop.product.models.Product;
import com.ecommerce.eshop.utils.excepctions.ExceptionUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    public CustomerOrder save(CustomerOrder customerOrder){
        if (customerOrder.getId() != null && orderRepository.existsById(customerOrder.getId())){
            throw new OrderCreationException(String.format(ExceptionUtils.ORDER_CANNOT_SAVE, customerOrder.getId()));
        }

        if (customerOrder.getProducts().isEmpty()){
            throw new OrderCreationException(ExceptionUtils.ORDER_WITHOUT_ANY_PRODUCTS);
        }
        customerOrder.setTotalAmount(countTotalAmount(customerOrder));
        customerOrder.setTotalQuantity(countTotalQuantity(customerOrder));

        customerOrder.setOrderStatus(OrderStatus.CREATED);
        return orderRepository.save(customerOrder);
    }

    private BigDecimal countTotalAmount(CustomerOrder customerOrder){
        BigDecimal totalAmount = BigDecimal.ZERO;
        for (Product product : customerOrder.getProducts()) {
            totalAmount = totalAmount.add(product.getPrice());
        }
        return totalAmount;
    }

    private Integer countTotalQuantity(CustomerOrder customerOrder){
        return customerOrder.getProducts().size();
    }

    public Optional<CustomerOrder> getByID(Long id){
        return orderRepository.findById(id);
    }

    public List<CustomerOrder> getAll(){
        return orderRepository.findAll();
    }

    public List<CustomerOrder> getAllThatIncludesProduct(Product product){
        return orderRepository.findAllByProductsIsContaining(product);
    }

    public List<CustomerOrder> getAllByNewest(){
        return orderRepository.findAllByOrderByCreationTimeDesc();
    }

    public CustomerOrder deleteById(Long id){
        Optional<CustomerOrder> orderFromDB = orderRepository.findById(id);
        if (orderFromDB.isEmpty()){
            throw new OrderNotFoundException(String.format(ExceptionUtils.ORDER_NOT_FOUND, id));
        }
        orderRepository.deleteById(id);
        return orderFromDB.get();
    }


}
