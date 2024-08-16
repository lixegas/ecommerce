package com.lixega.ecommerce.order.client;

import com.lixega.ecommerce.sdk.core.model.dto.OrderItemDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "warehouse-service")
public interface WarehouseClient {

    @PostMapping("/reserve")
    boolean reserveStock(@RequestBody List<OrderItemDTO> items);
}
