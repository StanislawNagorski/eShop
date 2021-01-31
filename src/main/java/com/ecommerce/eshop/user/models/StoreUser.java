package com.ecommerce.eshop.user.models;

import com.ecommerce.eshop.order.models.CustomerOrder;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Entity
@Builder
@NoArgsConstructor
@Data
public class StoreUser {

    @Id
    private String login;
    @NotEmpty
    private String password;
    @NotEmpty
    private UserRole role;
    @OneToMany
    private List<CustomerOrder> customerOrders;
}
