package com.lixega.ecommerce.product.mapper;

import com.lixega.ecommerce.product.model.entity.Product;
import com.lixega.ecommerce.product.model.entity.ProductCollection;
import com.lixega.ecommerce.product.model.request.ProductCreationRequest;
import com.lixega.ecommerce.product.model.response.ProductResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Named("ProductMapper")
@Mapper(imports = Instant.class, componentModel = "spring")
public interface ProductMapper {


    @Mapping(target = "createdAt", expression = "java(java.time.Instant.now())")
    Product mapToProduct(ProductCreationRequest request);


    @Mapping(target = "productId", source = "product.id")
    ProductCollection mapToProductCollection(ProductCreationRequest request, Product product);


    ProductResponse mapToResponse(Product product, ProductCollection productCollection);
}
