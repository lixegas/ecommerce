package com.lixega.ecommerce.sdk.oauth2.config;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimNames;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class JwtAuthConverter implements Converter<Jwt, AbstractAuthenticationToken> {

    private final JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter =
            new JwtGrantedAuthoritiesConverter();

    @Override
    public AbstractAuthenticationToken convert(@NonNull Jwt jwt) {
        Collection<GrantedAuthority> authorities = Stream.concat(
                jwtGrantedAuthoritiesConverter.convert(jwt).stream(),
                extractResourceRoles(jwt).stream()
        ).collect(Collectors.toSet());

        return new JwtAuthenticationToken(jwt, authorities, getPrincipleClaimName(jwt));
    }

    private Collection<? extends GrantedAuthority> extractResourceRoles(Jwt jwt){
        if(jwt.getClaim("resource_access") == null){
            return new HashSet<>();
        }
        Map<String, Object> resourceAccess = jwt.getClaim("resource_access");

        if(resourceAccess.get("ecommerce-api") == null){
            return new HashSet<>();
        }

        Map<String, Object> resource = (Map<String, Object>) resourceAccess.get("ecommerce-api");
        Collection<String> resourceRoles = (Collection<String>) resource.get("roles");

        return resourceRoles
                .stream()
                .map(role -> new SimpleGrantedAuthority(STR."ROLE_\{role}"))
                .collect(Collectors.toSet());
    }

    private String getPrincipleClaimName(Jwt jwt){
        String claimName = JwtClaimNames.SUB;
        return jwt.getClaim(claimName);
    }

}
