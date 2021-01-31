package com.ecommerce.eshop.user.repositories;

import com.ecommerce.eshop.user.models.StoreUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoreUserRepository extends JpaRepository<StoreUser, String> {


}
