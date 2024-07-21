package com.lixega.ecommerce.auth.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ValidationTokenResponse {
    private Long userId;
    private String email;
}