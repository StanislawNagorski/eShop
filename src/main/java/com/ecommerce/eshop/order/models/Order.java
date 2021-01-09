package com.ecommerce.eshop.order.models;

import com.ecommerce.eshop.product.models.Product;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@NoArgsConstructor
@Data
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToMany
    private List<Product> products;
    private BigDecimal totalAmount;
    private Integer totalQuantity;
    @CreationTimestamp
    private LocalDateTime creationTime;
    @UpdateTimestamp
    private LocalDateTime updateTime;
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;
}
