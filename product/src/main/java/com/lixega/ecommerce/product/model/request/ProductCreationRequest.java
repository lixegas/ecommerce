package com.lixega.ecommerce.product.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Map;

@Data
@AllArgsConstructor
public class ProductCreationRequest {

    private String ean;
    private BigDecimal price;

    private String slug;
    private Long productId;

    private String name;
    private String description;
    private String informations;

    private Map<String, String> specifics;
}
