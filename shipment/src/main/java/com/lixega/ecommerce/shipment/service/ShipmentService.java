package com.lixega.ecommerce.shipment.service;

import com.lixega.ecommerce.sdk.core.model.dto.ShipmentDto;
import com.lixega.ecommerce.sdk.core.model.enumeration.ShipmentStatus;
import com.lixega.ecommerce.shipment.mapper.ShipmentMapper;
import com.lixega.ecommerce.shipment.model.entity.Shipment;

import com.lixega.ecommerce.shipment.repository.ShipmentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.UUID;


@Service
@AllArgsConstructor
public class ShipmentService {

    private final ShipmentRepository shipmentRepository;
    private final ShipmentMapper shipmentMapper;

    public Shipment createShipment(ShipmentDto shipmentDto) {
        Shipment shipment = shipmentMapper.toShipment(shipmentDto);
        return shipmentRepository.save(shipment);
    }

    private static String generateTrackingNumber() {
        UUID uuid = UUID.randomUUID();
        return "TRK-" + uuid.toString().replace("-", "").toUpperCase();
    }
}

