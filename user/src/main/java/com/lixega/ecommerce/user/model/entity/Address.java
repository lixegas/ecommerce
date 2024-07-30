package com.lixega.ecommerce.user.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "address")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String recipientName;
    private String streetAddress;
    private String city;
    private String state;
    private String zipCode;
    private String country;

    private boolean primary;
    private boolean enabled;
}
