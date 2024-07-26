package com.lixega.ecommerce.sdk.core.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CredentialsDTO {

    private Long id;
    private String email;
    private String phoneNumber;
    private Instant createdAt;
}
