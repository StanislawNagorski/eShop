package com.ecommerce.eshop.user.service;

import com.ecommerce.eshop.user.exceptions.UserCreationException;
import com.ecommerce.eshop.user.exceptions.UserNotFoundException;
import com.ecommerce.eshop.user.models.StoreUser;
import com.ecommerce.eshop.user.repositories.StoreUserRepository;
import com.ecommerce.eshop.utils.excepctions.ExceptionUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.ecommerce.eshop.utils.excepctions.ExceptionUtils.*;

@Service
@RequiredArgsConstructor
public class StoreUserService {

    StoreUserRepository repository;

    public StoreUser save(StoreUser user){
        if (repository.existsById(user.getLogin())){
            throw new UserCreationException(String.format(USER_CANNOT_SAVE, user.getLogin()));
        }
        user.setCustomerOrders(new ArrayList<>());
        return repository.save(user);
    }

    public List<StoreUser> getAllUsers(){
        return repository.findAll();
    }

    public StoreUser getByLogin(StoreUser user){
        Optional<StoreUser> userOptional = repository.findById(user.getLogin());
        if (userOptional.isEmpty()){
            throw new UserNotFoundException(String.format(USER_NOT_FOUND, user.getLogin()));
        }
        return userOptional.get();
    }

}
