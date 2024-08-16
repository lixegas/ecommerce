package com.lixega.ecommerce.warehouse.repository;

import com.lixega.ecommerce.warehouse.model.entity.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WarehouseRepository extends JpaRepository<Warehouse, Long> {
}
