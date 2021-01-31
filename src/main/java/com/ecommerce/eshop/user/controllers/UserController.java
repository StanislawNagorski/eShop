package com.ecommerce.eshop.user.controllers;

import com.ecommerce.eshop.user.models.StoreUser;
import com.ecommerce.eshop.user.service.StoreUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true", allowedHeaders = "*")
@RequiredArgsConstructor
public class UserController {

    private final StoreUserService service;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<StoreUser> getAllUsers(){
        return service.getAllUsers();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public StoreUser saveUser(StoreUser user){
        return service.save(user);
    }



}
