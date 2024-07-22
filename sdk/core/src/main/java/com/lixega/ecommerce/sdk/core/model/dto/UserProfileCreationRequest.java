package com.lixega.ecommerce.sdk.core.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileCreationRequest {
    private Long id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
}
