package com.lixega.ecommerce.auth.repository;

import com.lixega.ecommerce.auth.model.entity.AccessHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccessHistoryRepository extends JpaRepository<AccessHistory, Long> {
}