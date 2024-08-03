package com.lixega.ecommerce.product.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponse {

    private String ean;
    private BigDecimal price;

    private String slug;

    private Long productId;
    private String name;
    private String description;
    private String informations;

    private Map<String, String> specifics;
}
