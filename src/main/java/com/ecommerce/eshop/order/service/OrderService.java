package com.ecommerce.eshop.order.service;

import com.ecommerce.eshop.order.models.Order;
import com.ecommerce.eshop.order.repositories.OrderRepository;
import com.ecommerce.eshop.product.models.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {
    private OrderRepository orderRepository;

    public Order save(Order order){
        return order;
    }

    public Optional<Order> getByID(Long id){
        return orderRepository.findById(id);
    }

    public List<Order> getAll(){
        return orderRepository.findAll();
    }

    public List<Order> getAllThatIncludesProduct(Product product){
        return Collections.emptyList();
    }


}
