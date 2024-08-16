package com.lixega.ecommerce.warehouse.repository;

import com.lixega.ecommerce.warehouse.model.entity.ProductLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductLocationRepository extends JpaRepository<ProductLocation, Long> {
    List<ProductLocation> findAllByWarehouseId(Long warehouseId);
    ProductLocation findByWarehouseIdAndProductId(Long warehouseId, Long productId);
    Optional<ProductLocation> findByProductId(Long productId);
}