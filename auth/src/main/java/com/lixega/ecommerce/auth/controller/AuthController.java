package com.lixega.ecommerce.auth.controller;

import com.lixega.ecommerce.auth.model.dto.request.LoginRequest;
import com.lixega.ecommerce.auth.model.dto.request.RefreshTokenRequest;
import com.lixega.ecommerce.auth.model.dto.request.UserRegistrationRequest;
import com.lixega.ecommerce.auth.model.dto.request.ValidationTokenRequest;
import com.lixega.ecommerce.auth.model.dto.response.JWTResponse;
import com.lixega.ecommerce.auth.model.dto.response.UserRegistrationResponse;
import com.lixega.ecommerce.auth.model.dto.response.ValidationTokenResponse;
import com.lixega.ecommerce.auth.service.AuthService;
import com.lixega.ecommerce.auth.service.RefreshTokenService;
import com.lixega.ecommerce.auth.service.ValidationTokenService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/auth")
@AllArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final RefreshTokenService refreshTokenService;
    private final ValidationTokenService validationTokenService;

    @PostMapping("login")
    public JWTResponse login(@RequestBody LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }

    @PostMapping("register")
    public UserRegistrationResponse register(@Valid @RequestBody UserRegistrationRequest request) {
        return authService.register(request);
    }

    @PostMapping("refresh")
    public JWTResponse refresh(@RequestBody RefreshTokenRequest refreshTokenRequestDTO){
        return refreshTokenService.refreshToken(refreshTokenRequestDTO);
    }

    @PostMapping("validate")
    public ValidationTokenResponse validate(@RequestBody ValidationTokenRequest request){
        return validationTokenService.validateToken(request);
    }
}
