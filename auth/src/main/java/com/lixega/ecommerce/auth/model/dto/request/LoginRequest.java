package com.lixega.ecommerce.auth.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {

    @NotBlank(message = "Username field is mandatory")
    private String email;
    @NotBlank(message = "Password field is mandatory")
    private String password;
}
