package com.lixega.ecommerce.product.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.math.BigDecimal;
import java.time.Instant;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String ean;
    private BigDecimal price;

    @Column(name = "create_At")
    private Instant createdAt;
    private String slug;

    @Column(name = "warehouse_id")
    private Long warehouseId;
}
