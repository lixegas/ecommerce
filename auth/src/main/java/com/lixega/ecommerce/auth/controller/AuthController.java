package com.lixega.ecommerce.auth.controller;



import com.lixega.ecommerce.auth.model.dto.request.LoginRequest;
import com.lixega.ecommerce.auth.model.dto.request.RefreshTokenRequestDTO;
import com.lixega.ecommerce.auth.model.dto.request.RegistrationRequest;
import com.lixega.ecommerce.auth.model.dto.response.JWTResponse;
import com.lixega.ecommerce.auth.model.dto.response.RegistrationResponse;
import com.lixega.ecommerce.auth.service.AuthService;
import com.lixega.ecommerce.auth.service.RefreshTokenService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;


import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final RefreshTokenService refreshTokenService;


    @PostMapping("login")
    public JWTResponse login(@RequestBody LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }


    @PostMapping("register")
    public RegistrationResponse register(@Valid @RequestBody RegistrationRequest request) {
        return authService.register(request);
    }

    @PostMapping("refresh")
    public JWTResponse refresh(@RequestBody RefreshTokenRequestDTO refreshTokenRequestDTO){
        return refreshTokenService.refreshToken(refreshTokenRequestDTO);
    }

}
