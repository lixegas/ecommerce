package com.lixega.ecommerce.sdk.oauth2.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.SessionManagementConfigurer;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthConverter jwtAuthConverter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(this::configurateCsrf)
                .authorizeHttpRequests(this::configurateHttpRequests);

        http
                .oauth2ResourceServer(this::configureOuath2ResourceServer);

        http
                .sessionManagement(this::configureSessionManagement);

        return http.build();
    }

    private void configurateCsrf(CsrfConfigurer<HttpSecurity> httpSecurityCsrfConfigurer) {
        httpSecurityCsrfConfigurer.disable();
    }

    private void configurateHttpRequests(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry registry) {
        registry
                .anyRequest()
                .authenticated();
    }

    private void configureSessionManagement(SessionManagementConfigurer<HttpSecurity> sessionManagementConfigurer) {
        sessionManagementConfigurer
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    private void configureOuath2ResourceServer(OAuth2ResourceServerConfigurer<HttpSecurity> oauth2Configurer) {
        oauth2Configurer
                .jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthConverter));
    }
}
