package com.lixega.ecommerce.warehouse.service;

import com.lixega.ecommerce.warehouse.mapper.WarehouseMapper;
import com.lixega.ecommerce.warehouse.model.entity.ProductLocation;
import com.lixega.ecommerce.warehouse.model.entity.Warehouse;
import com.lixega.ecommerce.warehouse.model.request.ProductLocationRequest;
import com.lixega.ecommerce.warehouse.model.request.WarehouseRequest;
import com.lixega.ecommerce.warehouse.repository.ProductLocationRepository;
import com.lixega.ecommerce.warehouse.repository.WarehouseRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@AllArgsConstructor
public class WarehouseService {

    private final WarehouseRepository warehouseRepository;
    private final WarehouseMapper warehouseMapper;


    public Warehouse newWarehouse(WarehouseRequest request){

        Warehouse warehouse = warehouseMapper.mapToWarehouse(request);
        return warehouseRepository.save(warehouse);
    }


    public ResponseEntity<Warehouse> findWarehouseById(Long warehouseId) {
        Optional<Warehouse> warehouseOptional = warehouseRepository.findById(warehouseId);

        if (warehouseOptional.isPresent()) {
            Warehouse warehouseFound = warehouseOptional.get();
            return ResponseEntity.ok(warehouseFound);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Warehouse not found");
        }
    }


}


