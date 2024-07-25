package com.lixega.ecommerce.sdk.core.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtValidationResponse {
    private Long userId;
    private String jwt;

    List<String> roles = new ArrayList<>();
}
