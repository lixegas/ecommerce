package com.lixega.ecommerce.product.model.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;


@Document(collection = "productCollection")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductCollection {

    @Id
    private String documentId;
    private Long productId;

    private String name;
    private String description;
    private String informations;

    private Map<String, String> specifics;
}
