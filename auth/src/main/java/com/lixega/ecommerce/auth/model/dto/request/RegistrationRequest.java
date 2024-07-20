package com.lixega.ecommerce.auth.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationRequest {

    @NotBlank(message = "Email field is mandatory")
    private String email;
    @NotBlank(message = "Password field is mandatory")
    private String password;
}
