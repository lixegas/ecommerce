package com.lixega.ecommerce.warehouse.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;

@Data
@AllArgsConstructor
public class WarehouseRequest {

    private String name;
    private String address;
    private Instant createdAt;
}
