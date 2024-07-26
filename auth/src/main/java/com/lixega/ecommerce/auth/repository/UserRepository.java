package com.lixega.ecommerce.auth.repository;


import com.lixega.ecommerce.auth.model.entity.UserCredentials;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserCredentials, Long> {
    Optional<UserCredentials> findByEmail(String email);
    UserCredentials getUserById(Long id);
}
