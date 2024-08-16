package com.lixega.ecommerce.order.mapper;


import com.lixega.ecommerce.order.model.dto.OrderDTO;
import com.lixega.ecommerce.order.model.entity.Order;
import com.lixega.ecommerce.order.model.entity.OrderItem;

import org.mapstruct.Mapper;
import org.mapstruct.Named;

import java.time.Instant;

@Named("OrderMapper")
@Mapper(imports = Instant.class, componentModel = "spring")
public interface OrderMapper {

    OrderDTO toOrderDTO(Order order);
}