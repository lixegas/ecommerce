package com.lixega.ecommerce.user.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAddressCreationRequest {
    private String recipientName;
    private String streetAddress;
    private String city;
    private String state;
    private String zipCode;
    private String country;
}
