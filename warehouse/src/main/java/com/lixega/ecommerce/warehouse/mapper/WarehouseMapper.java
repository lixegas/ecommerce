package com.lixega.ecommerce.warehouse.mapper;

import com.lixega.ecommerce.warehouse.model.entity.Warehouse;
import com.lixega.ecommerce.warehouse.model.request.WarehouseRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;


import java.time.Instant;

@Named("WarehouseMapper")
@Mapper(imports = Instant.class, componentModel = "spring")

public interface WarehouseMapper {

    @Mapping(target = "createdAt", expression = "java(java.time.Instant.now())")
    Warehouse mapToWarehouse(WarehouseRequest request);
}
