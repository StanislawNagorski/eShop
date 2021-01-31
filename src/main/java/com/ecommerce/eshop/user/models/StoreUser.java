package com.ecommerce.eshop.user.models;

import com.ecommerce.eshop.order.models.CustomerOrder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Entity
@NoArgsConstructor
@Data
public class StoreUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty
    private String login;
    @NotEmpty
    private String password;
    @NotEmpty
    private String role;
    @OneToMany
    private List<CustomerOrder> customerOrders;
}
