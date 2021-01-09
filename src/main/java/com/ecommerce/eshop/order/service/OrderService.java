package com.ecommerce.eshop.order.service;

import com.ecommerce.eshop.order.models.Order;
import com.ecommerce.eshop.order.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {
    private OrderRepository orderRepository;

    public Order save(Order order){
        return order;
    }


}
