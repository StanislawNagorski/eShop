package com.ecommerce.eshop.user.repositories;

import com.ecommerce.eshop.user.models.StoreUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StoreUserRepository extends JpaRepository<StoreUser, Long> {

    Optional<StoreUser> findByLogin(String login);
}
