package com.ecommerce.eshop.user.service;

import com.ecommerce.eshop.user.exceptions.UserCreationException;
import com.ecommerce.eshop.user.exceptions.UserNotFoundException;
import com.ecommerce.eshop.user.models.StoreUser;
import com.ecommerce.eshop.user.repositories.StoreUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.ecommerce.eshop.utils.excepctions.ExceptionUtils.USER_CANNOT_SAVE;
import static com.ecommerce.eshop.utils.excepctions.ExceptionUtils.USER_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class StoreUserService {

    private final StoreUserRepository repository;

    public StoreUser save(StoreUser user){
        if (user.getId() != null && repository.existsById(user.getId())){
            throw new UserCreationException(String.format(USER_CANNOT_SAVE, user.getLogin()));
        }

        user.setCustomerOrders(new ArrayList<>());
        return repository.save(user);
    }

    public List<StoreUser> getAllUsers(){
        return repository.findAll();
    }

    public StoreUser getByLogin(String userLogin){
        Optional<StoreUser> userOptional = repository.findByLogin(userLogin);
        if (userOptional.isEmpty()){
            throw new UserNotFoundException(String.format(USER_NOT_FOUND, userLogin));
        }
        return userOptional.get();
    }

}
