package com.ecommerce.eshop;

import com.ecommerce.eshop.product.service.CategoryService;
import com.ecommerce.eshop.product.service.ProductService;
import com.ecommerce.eshop.user.models.StoreUser;
import com.ecommerce.eshop.user.models.UserRole;
import com.ecommerce.eshop.user.service.StoreUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class Invoker implements CommandLineRunner {

    private final StoreUserService service;

    @Override
    @Transactional
    public void run(String... args) {

        StoreUser user = new StoreUser();
        user.setLogin("admin");
        user.setPassword("admin");
        user.setRole(UserRole.ADMIN.name());
        service.save(user);
    }
}
