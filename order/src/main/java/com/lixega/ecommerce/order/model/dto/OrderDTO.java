package com.lixega.ecommerce.order.model.dto;

import com.lixega.ecommerce.order.model.enumeration.OrderStatus;
import com.lixega.ecommerce.sdk.core.model.dto.OrderItemDTO;
import com.lixega.ecommerce.sdk.core.model.enumeration.ShipmentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderDTO {

    private Long id;
    private OrderStatus status;
    private List<OrderItemDTO> items;
    private ShipmentStatus shipmentStatus;
    private String trackingNumber;
    private String carrier;
    private Instant shippedDate;
    private Instant deliveryDate;
}