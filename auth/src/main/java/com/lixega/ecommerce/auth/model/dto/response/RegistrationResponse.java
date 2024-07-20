package com.lixega.ecommerce.auth.model.dto.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationResponse {
    private String email;
    private String password;
    private Instant createdAt;
}
