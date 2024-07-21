package com.lixega.ecommerce.apigateway.service;

import com.lixega.ecommerce.apigateway.client.AuthClient;
import com.lixega.ecommerce.sdk.security.model.dto.request.JwtValidationRequest;
import com.lixega.ecommerce.sdk.security.model.dto.response.JwtValidationResponse;
import com.lixega.ecommerce.sdk.security.model.mapper.JwtMapper;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthService {
    private final AuthClient authClient;

    private final JwtMapper jwtMapper;

    public Authentication getAuthenticationFromJwt(String jwt){
        JwtValidationRequest request = new JwtValidationRequest(jwt);

        JwtValidationResponse response = authClient.validateJwt(request);

        return jwtMapper.toAuthentication(response);
    }
}
