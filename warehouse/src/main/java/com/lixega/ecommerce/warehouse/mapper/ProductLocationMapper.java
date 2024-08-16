package com.lixega.ecommerce.warehouse.mapper;

import com.lixega.ecommerce.warehouse.model.entity.ProductLocation;

import com.lixega.ecommerce.warehouse.model.entity.Warehouse;
import com.lixega.ecommerce.warehouse.model.request.ProductLocationRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import java.time.Instant;

@Named("ProductLocationMapper")
@Mapper(imports = Instant.class, componentModel = "spring")
public interface ProductLocationMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", expression = "java(java.time.Instant.now())")
    @Mapping(target = "updatedAt", expression = "java(java.time.Instant.now())")
    @Mapping(target = "productId", source = "productId")
    @Mapping(target = "warehouse", source = "warehouse")
    ProductLocation toProductLocation(ProductLocationRequest request, Warehouse warehouse, Long productId);

}