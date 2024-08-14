package com.lixega.ecommerce.apigateway.config;

import com.lixega.ecommerce.apigateway.service.AuthService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Configuration
@AllArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final AuthService authService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        System.out.println(request.getHeader("Authorization"));

        String authHeader = request.getHeader("Authorization");
        if(authHeader == null || !authHeader.startsWith("Bearer ")){
            filterChain.doFilter(request, response);
            return;
        }

        String jwt = authHeader.split(" ")[1];

        JwtValidationResponse validationResponse;
        try{
            validationResponse = authService.validateJwt(jwt);
        } catch(Exception e){
            throw new ServletException("Invalid token provided");
        }

        response.addHeader("X-USER", String.valueOf(validationResponse.getUserId()));
        response.addHeader("X-ROLES", String.join(",", validationResponse.getRoles()));

        filterChain.doFilter(request, response);
    }
}
