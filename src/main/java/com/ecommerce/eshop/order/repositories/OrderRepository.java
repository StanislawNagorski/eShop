package com.ecommerce.eshop.order.repositories;

import com.ecommerce.eshop.order.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    //by total price
    //by total quantity
    //by product
}
