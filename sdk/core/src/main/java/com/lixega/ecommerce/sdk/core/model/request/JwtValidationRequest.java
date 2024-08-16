package com.lixega.ecommerce.sdk.core.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtValidationRequest {
    private String jwt;
}
