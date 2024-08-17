package com.lixega.ecommerce.order.client;

import com.lixega.ecommerce.sdk.core.model.dto.ShipmentDto;
import com.lixega.ecommerce.shipment.model.entity.Shipment;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@FeignClient(name = "shipment-service")
public interface ShipmentClient {

    @PostMapping("/")
    ShipmentDto createShipment(@RequestBody ShipmentDto shipmentDto);
}


