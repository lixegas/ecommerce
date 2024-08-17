package com.lixega.ecommerce.apigateway.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthService {
    private final AuthClient authClient;

    public JwtValidationResponse validateJwt(String jwt){
        JwtValidationRequest request = new JwtValidationRequest(jwt);
        return authClient.validateJwt(request);
    }
}
