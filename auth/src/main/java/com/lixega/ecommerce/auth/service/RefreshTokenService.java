package com.lixega.ecommerce.auth.service;

import com.lixega.ecommerce.auth.config.JWTUtils;
import com.lixega.ecommerce.auth.model.entity.Access;
import com.lixega.ecommerce.auth.model.entity.RefreshToken;
import com.lixega.ecommerce.auth.model.dto.response.LoginResponse;
import com.lixega.ecommerce.auth.model.entity.Session;
import com.lixega.ecommerce.auth.repository.AccessRepository;
import com.lixega.ecommerce.auth.repository.UserRepository;
import com.lixega.ecommerce.auth.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {
    private final SessionService sessionService;

    private final RefreshTokenRepository refreshTokenRepository;
    private final AccessRepository accessRepository;

    private long refreshTokenTtlSeconds = 2592000L;

    protected RefreshToken createRefreshToken(Access access) {
        RefreshToken refreshToken = buildRefreshToken(access);

        return refreshTokenRepository.save(refreshToken);
    }

    private RefreshToken buildRefreshToken(Access access) {
        String refreshToken = UUID.randomUUID().toString();
        RefreshToken.RefreshTokenBuilder refreshTokenBuilder = RefreshToken.builder()
                .token(refreshToken)
                .expiryDate(Instant.now().plusSeconds(refreshTokenTtlSeconds))
                .valid(true)
                .access(access);

        return refreshTokenBuilder.build();
    }

    public Session refresh(String token) {
        Optional<RefreshToken> refreshTokenOptional = refreshTokenRepository.findById(token);
        if (refreshTokenOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid refresh token provided");
        }

        RefreshToken refreshToken = refreshTokenOptional.get();
        if (Instant.now().isAfter(refreshToken.getExpiryDate())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Refresh token has expired. Please log in again");
        }
        if (!refreshToken.isValid()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Refresh token has been invalidated. Please log in again");
        }

        Optional<Access> accessOptional = accessRepository.findByRefreshToken(refreshToken);
        if (accessOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid access. Please log in again");
        }

        Access access = accessOptional.get();

        return sessionService.createSession(access);
    }
}
