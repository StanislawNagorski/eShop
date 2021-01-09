package com.ecommerce.eshop.order.repositories;

import com.ecommerce.eshop.order.models.CustomerOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<CustomerOrder, Long> {
    //by total price
    //by total quantity
    //by product
}
