package com.lixega.ecommerce.user.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAddressDTO {
    private Long id;

    private String recipientName;
    private String streetAddress;
    private String city;
    private String state;
    private String zipCode;
    private String country;

    private boolean primary;
}
