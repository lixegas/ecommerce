package com.lixega.ecommerce.auth.repository;


import com.lixega.ecommerce.auth.model.entity.Credentials;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CredentialsRepository extends JpaRepository<Credentials, Long> {
    Optional<Credentials> findByEmail(String email);
    Optional<Credentials> findByPassword(String password);
}
