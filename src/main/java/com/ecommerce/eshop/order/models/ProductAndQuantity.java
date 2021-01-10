package com.ecommerce.eshop.order.models;

import com.ecommerce.eshop.product.models.Product;

import javax.persistence.Entity;
import javax.persistence.Id;

public class ProductAndQuantity {

    private Product product;
    private Integer quantity;

}
