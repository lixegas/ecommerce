package com.lixega.ecommerce.apigateway.service;

import com.lixega.ecommerce.apigateway.client.AuthClient;
import com.lixega.ecommerce.sdk.core.model.dto.request.JwtValidationRequest;
import com.lixega.ecommerce.sdk.core.model.dto.response.JwtValidationResponse;
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
