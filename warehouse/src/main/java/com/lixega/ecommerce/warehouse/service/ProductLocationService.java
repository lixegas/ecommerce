package com.lixega.ecommerce.warehouse.service;

import com.lixega.ecommerce.sdk.core.model.dto.OrderItemDTO;
import com.lixega.ecommerce.warehouse.mapper.ProductLocationMapper;
import com.lixega.ecommerce.warehouse.model.entity.ProductLocation;
import com.lixega.ecommerce.warehouse.model.entity.Warehouse;
import com.lixega.ecommerce.warehouse.model.request.ProductLocationRequest;
import com.lixega.ecommerce.warehouse.repository.ProductLocationRepository;
import com.lixega.ecommerce.warehouse.repository.WarehouseRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.List;


@Service
@AllArgsConstructor
public class ProductLocationService {

    private final ProductLocationRepository productLocationRepository;
    private final WarehouseRepository warehouseRepository;
    private final ProductLocationMapper productLocationMapper;


    public ResponseEntity<ProductLocation> getProduct(Long warehouseId, Long productId){
        if (!warehouseRepository.existsById(warehouseId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        ProductLocation productLocation = productLocationRepository.findByWarehouseIdAndProductId(warehouseId, productId);

        if (productLocation == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.ok(productLocation);
    }


    public ResponseEntity<ProductLocation> updateProduct(Long warehouseId, Long productId, ProductLocationRequest request) {

        Warehouse warehouse = warehouseRepository.findById(warehouseId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Warehouse does not exist"));

        ProductLocation productLocation = productLocationRepository.findByWarehouseIdAndProductId(warehouseId, productId);

        if (productLocation == null) {
            ProductLocation newProductLocation = productLocationMapper.toProductLocation(request, warehouse, productId);
            productLocationRepository.save(newProductLocation);
            return ResponseEntity.status(HttpStatus.CREATED).body(newProductLocation);
        } else {
            productLocation.setQuantity(productLocation.getQuantity() + request.getQuantity());
            productLocation.setUpdatedAt(Instant.now());
            productLocationRepository.save(productLocation);
            return ResponseEntity.ok(productLocation);
        }
    }


    public boolean reserveItems(List<OrderItemDTO> items) {
        for (OrderItemDTO item : items) {
            ProductLocation product = productLocationRepository.findByProductId(item.getProductId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));
            if (product.getQuantity() < item.getQuantity()) {
                return false;
            }
            product.setQuantity(product.getQuantity() - item.getQuantity());
            productLocationRepository.save(product);
        }
        return true;
    }
}
