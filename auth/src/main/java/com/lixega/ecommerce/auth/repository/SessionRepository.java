package com.lixega.ecommerce.auth.repository;

import com.lixega.ecommerce.auth.model.entity.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionRepository extends JpaRepository<Session, String> {
}
