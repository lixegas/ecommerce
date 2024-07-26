package com.lixega.ecommerce.auth.service;

import com.lixega.ecommerce.auth.config.JWTUtils;
import com.lixega.ecommerce.auth.model.dto.response.RefreshResponse;
import com.lixega.ecommerce.auth.model.entity.UserCredentials;
import com.lixega.ecommerce.auth.model.entity.RefreshToken;
import com.lixega.ecommerce.auth.model.dto.request.RefreshTokenRequest;
import com.lixega.ecommerce.auth.model.dto.response.LoginResponse;
import com.lixega.ecommerce.auth.repository.UserRepository;
import com.lixega.ecommerce.auth.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {
    @Value("${security.refresh-token.expiration-millis}")
    private Long jwtExpirationInMillis;

    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JWTUtils jwtUtils;

    public RefreshToken createRefreshToken(String email, int daysToExpire) {
        Optional<UserCredentials> credentialsOptional = userRepository.findByEmail(email);
        if (credentialsOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "user does not exist.");
        }

        RefreshToken refreshToken = RefreshToken.builder()
                .userCredentials(credentialsOptional.get())
                .token(UUID.randomUUID().toString())
                .expiryDate(Instant.now().plus(daysToExpire, ChronoUnit.DAYS))
                .build();

        return refreshTokenRepository.save(refreshToken);
    }



    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    public void verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            String errorMessage = String.format("%s! Red. Please make a new login!", token);
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, errorMessage );
        }
    }

    public RefreshResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
        Optional<RefreshToken> tokenOptional = findByToken(refreshTokenRequest.getRefreshToken());
        if (tokenOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid refresh token provided");
        }

        RefreshToken token = tokenOptional.get();
        verifyExpiration(token);

        UserCredentials userCredentials = token.getUserCredentials();
        String accessToken = jwtUtils.generateTokenWithEmail(userCredentials.getEmail());

        return new RefreshResponse(accessToken);
    }
}
