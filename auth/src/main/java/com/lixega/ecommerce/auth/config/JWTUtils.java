package com.lixega.ecommerce.auth.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.time.Instant;
import java.util.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class JWTUtils {
    @Value("${security.jwt.expiration-millis}")
    private Long jwtExpirationMillis;

    private final RSAKeyProperties rsaKeyProperties;

    public String getSubject(String jwt) {
        JwtParser jwtParser =
                Jwts.parser()
                        .verifyWith(rsaKeyProperties.publicKey())
                        .build();
        try {
            Claims claims = jwtParser.parseSignedClaims(jwt).getPayload();
            return claims.getSubject();
        } catch (Exception e) {
            log.error("Encounter an error while decoding JWT:", e);
            return null;
        }
    }

    public String generateToken(String subject) {
        return Jwts
                .builder()
                .signWith(rsaKeyProperties.privateKey())
                .issuedAt(Date.from(Instant.now()))
                .subject(subject)
                .expiration(Date.from(Instant.now().plusMillis(jwtExpirationMillis)))
                .compact();
    }

    public String generateToken(String subject, List<String> roles) {
        return Jwts
                .builder()
                .signWith(rsaKeyProperties.privateKey())
                .issuedAt(Date.from(Instant.now()))
                .subject(subject)
                .claims(Map.of("roles", roles.toString()))
                .expiration(Date.from(Instant.now().plusMillis(jwtExpirationMillis)))
                .compact();
    }
}