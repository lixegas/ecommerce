package com.lixega.ecommerce.warehouse.controller;


import com.lixega.ecommerce.sdk.core.model.dto.OrderItemDTO;
import com.lixega.ecommerce.warehouse.model.entity.ProductLocation;
import com.lixega.ecommerce.warehouse.model.entity.Warehouse;

import com.lixega.ecommerce.warehouse.model.request.ProductLocationRequest;
import com.lixega.ecommerce.warehouse.model.request.WarehouseRequest;
import com.lixega.ecommerce.warehouse.repository.ProductLocationRepository;
import com.lixega.ecommerce.warehouse.repository.WarehouseRepository;
import com.lixega.ecommerce.warehouse.service.ProductLocationService;
import com.lixega.ecommerce.warehouse.service.WarehouseService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;



@RestController
@RequestMapping("api/v1/warehouse")
@AllArgsConstructor
public class WarehouseController {

    private final WarehouseService warehouseService;
    private final WarehouseRepository warehouseRepository;
    private final ProductLocationRepository productLocationRepository;
    private final ProductLocationService productLocationService;



    @GetMapping("/")
    public List<Warehouse> getAllWarehouses() {
        return warehouseRepository.findAll();
    }


    @PostMapping("/")
    public Warehouse newWarehouse(@RequestBody WarehouseRequest request) {
        return warehouseService.newWarehouse(request);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Warehouse> getWarehouseById(@PathVariable Long id) {
        return warehouseService.findWarehouseById(id);
    }


    @GetMapping("/{warehouseId}/products")
    public List<ProductLocation> getAllProduct(@PathVariable Long warehouseId) {
        return productLocationRepository.findAllByWarehouseId(warehouseId);
    }


    @GetMapping("/{warehouseId}/products/{productId}")
    public ResponseEntity<ProductLocation> getProductByWarehouse(@PathVariable Long warehouseId, @PathVariable Long productId) {
        return productLocationService.getProduct(warehouseId,productId);
    }

    @PatchMapping("/{warehouseId}/products/{productId}")
    public ResponseEntity<ProductLocation> patchProduct(@PathVariable Long warehouseId, @PathVariable Long productId, @RequestBody ProductLocationRequest request) {
        return productLocationService.updateProduct(warehouseId, productId, request);
    }

    @PostMapping("/reserve")
    public ResponseEntity<Boolean> reserveStock(@RequestBody List<OrderItemDTO> items) {
        boolean isReserved = productLocationService.reserveItems(items);
        return ResponseEntity.ok(isReserved);
    }
}


