package com.lixega.ecommerce.sdk.core.model.dto;

import com.lixega.ecommerce.sdk.core.model.enumeration.ShipmentStatus;
import lombok.Data;


import java.time.Instant;

@Data
public class ShipmentDto {
    private String trackingNumber;
    private String carrier;
    private ShipmentStatus shipmentStatus;
    private Long order;
    private Instant shippedDate;
    private Instant deliveryDate;
}


