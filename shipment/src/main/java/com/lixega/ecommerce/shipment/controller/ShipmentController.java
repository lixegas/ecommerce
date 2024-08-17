package com.lixega.ecommerce.shipment.controller;

import com.lixega.ecommerce.sdk.core.model.dto.ShipmentDto;
import com.lixega.ecommerce.shipment.model.entity.Shipment;

import com.lixega.ecommerce.shipment.repository.ShipmentRepository;
import com.lixega.ecommerce.shipment.service.ShipmentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("api/v1/shipment")
@AllArgsConstructor
public class ShipmentController {

    private final ShipmentService shipmentService;
    private final ShipmentRepository shipmentRepository;

    @GetMapping("/")
    public ResponseEntity<List<Shipment>> getAllShipments() {
        List<Shipment> shipments = shipmentRepository.findAll();
        return ResponseEntity.ok(shipments);
    }

    @PostMapping("/")
    public ResponseEntity<Shipment> createShipment(@RequestBody ShipmentDto request) {
        Shipment createdShipment = shipmentService.createShipment(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdShipment);
    }
}

