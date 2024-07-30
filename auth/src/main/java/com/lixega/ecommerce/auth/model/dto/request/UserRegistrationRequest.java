package com.lixega.ecommerce.auth.model.dto.request;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegistrationRequest {
    @NotBlank(message = "Name field is mandatory")
    private String firstName;

    @NotBlank(message = "Lastname field is mandatory")
    private String lastName;

    @NotBlank(message = "Email field is mandatory")
    @Email(message = "Email should be valid")
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@(.+)$", message = "Email should be valid")
    private String email;

    @NotBlank(message = "Password field is mandatory")
    @Size(min = 8, message = "Password should be at least 8 characters long")
    private String password;

    @NotBlank(message = "Phone number is mandatory")
    @Pattern(regexp = "\\+?[0-9. ()-]{7,25}", message = "Phone number should be valid")
    private String phoneNumber;
}
