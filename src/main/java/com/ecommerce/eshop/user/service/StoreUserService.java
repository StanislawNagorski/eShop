package com.ecommerce.eshop.user.service;

import com.ecommerce.eshop.user.exceptions.UserCreationException;
import com.ecommerce.eshop.user.models.StoreUser;
import com.ecommerce.eshop.user.repositories.StoreUserRepository;
import com.ecommerce.eshop.utils.excepctions.ExceptionUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.ecommerce.eshop.utils.excepctions.ExceptionUtils.*;

@Service
@RequiredArgsConstructor
public class StoreUserService {

    StoreUserRepository repository;

    public StoreUser save(StoreUser user){
        if (repository.existsById(user.getLogin())){
            throw new UserCreationException(String.format(USER_CANNOT_SAVE, user.getLogin()));
        }
        return repository.save(user);
    }

}
