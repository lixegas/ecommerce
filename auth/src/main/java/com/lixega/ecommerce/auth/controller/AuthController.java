package com.lixega.ecommerce.auth.controller;

import com.lixega.ecommerce.auth.model.dto.request.LoginRequest;
import com.lixega.ecommerce.auth.model.dto.request.UserRegistrationRequest;
import com.lixega.ecommerce.auth.model.dto.response.LoginResponse;
import com.lixega.ecommerce.auth.model.dto.response.UserRegistrationResponse;
import com.lixega.ecommerce.auth.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/auth")
@AllArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("login")
    public LoginResponse login(@RequestBody LoginRequest loginRequest, HttpServletRequest request, HttpServletResponse response) {
        return authService.login(loginRequest, request.getRemoteAddr(), response);
    }

    @PostMapping("register")
    public UserRegistrationResponse register(@Valid @RequestBody UserRegistrationRequest request) {
        return authService.register(request);
    }

    @GetMapping("refresh")
    public void refresh(HttpServletRequest request, HttpServletResponse response){
        authService.refresh(request, response);
    }
}
