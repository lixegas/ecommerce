package com.lixega.ecommerce.bff.config;

import com.lixega.ecommerce.auth.config.JWTUtils;
import com.lixega.ecommerce.auth.service.CredentialService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@AllArgsConstructor
public class JWTFilter extends OncePerRequestFilter {

    private final CredentialService credentialService;
    private final JWTUtils jwtUtils;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        String jwt = authHeader.split(" ")[1];

        String subject = jwtUtils.getSubject(jwt);
        if (subject == null) throw new ServletException("Invalid JWT provided.");

        UserDetails userFound = credentialService.loadUserByUsername(subject);
        Authentication authentication = new UsernamePasswordAuthenticationToken(userFound.getUsername(), userFound.getPassword());

        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(authentication);

        filterChain.doFilter(request, response);
    }
}
