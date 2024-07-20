package com.lixega.ecommerce.sdk.security.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtValidationResponse {
    private Long userId;
    private String email;
    private String jwt;
    private Instant expiredAt;
}
