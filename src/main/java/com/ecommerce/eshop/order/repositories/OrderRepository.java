package com.ecommerce.eshop.order.repositories;

import com.ecommerce.eshop.order.models.CustomerOrder;
import com.ecommerce.eshop.product.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<CustomerOrder, Long> {

    List<CustomerOrder> findAllByProductsIsContaining(Product product);
    List<CustomerOrder> findAllByOrderByCreationTimeDesc();
    List<CustomerOrder> findAllByOrderByTotalAmountDesc();
    //by total price
    //by total quantity
    //by product
}
