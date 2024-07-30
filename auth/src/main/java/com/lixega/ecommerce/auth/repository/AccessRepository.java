package com.lixega.ecommerce.auth.repository;

import com.lixega.ecommerce.auth.model.entity.Access;
import com.lixega.ecommerce.auth.model.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccessRepository extends JpaRepository<Access, Long> {
    Optional<Access> findByRefreshToken(RefreshToken token);
}