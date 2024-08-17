package com.lixega.ecommerce.order.mapper;


import com.lixega.ecommerce.order.model.dto.OrderDTO;
import com.lixega.ecommerce.order.model.entity.Order;
import com.lixega.ecommerce.sdk.core.model.dto.ShipmentDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import java.time.Instant;

@Named("OrderMapper")
@Mapper(imports = Instant.class, componentModel = "spring")
public interface OrderMapper {


    @Mapping(source = "order.id", target = "id")
    @Mapping(source = "order.status", target = "status")
    @Mapping(source = "order.items", target = "items")
    @Mapping(source = "shipmentDto.shipmentStatus", target = "shipmentStatus")
    @Mapping(source = "shipmentRequest.trackingNumber", target = "trackingNumber")
    @Mapping(source = "shipmentRequest.carrier", target = "carrier")
    @Mapping(source = "shipmentRequest.shippedDate", target = "shippedDate")
    @Mapping(source = "shipmentRequest.deliveryDate", target = "deliveryDate")
    OrderDTO toOrderDTO(Order order, ShipmentDto shipmentDto, ShipmentDto shipmentRequest);
}