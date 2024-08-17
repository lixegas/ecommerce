package com.lixega.ecommerce.shipment.model.entity;



import com.lixega.ecommerce.sdk.core.model.enumeration.ShipmentStatus;
import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.sql.ast.tree.insert.InsertStatement;

import java.time.Instant;

@Entity
@Table(name = "shipment")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Shipment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String trackingNumber;
    private String carrier;

    @Enumerated(EnumType.STRING)
    private ShipmentStatus shipmentStatus;

    @Column(name = "order_id")
    private Long order;

    @Column(name = "shipped_date")
    private Instant shippedDate;
    private Instant deliveryDate;
}


