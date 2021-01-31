package com.ecommerce.eshop.user.models;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;

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
}
