package com.lixega.ecommerce.auth.service;

import com.lixega.ecommerce.auth.config.JWTUtils;
import com.lixega.ecommerce.auth.model.entity.Credentials;
import com.lixega.ecommerce.auth.model.entity.RefreshToken;
import com.lixega.ecommerce.auth.model.dto.request.RefreshTokenRequestDTO;
import com.lixega.ecommerce.auth.model.dto.response.JWTResponse;
import com.lixega.ecommerce.auth.repository.CredentialsRepository;
import com.lixega.ecommerce.auth.repository.RefreshTokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;


@Service
@AllArgsConstructor
public class RefreshTokenService {


    private final CredentialsRepository credentialsRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JWTUtils jwtUtils;

    public RefreshToken createRefreshToken(String email) {
        Optional<Credentials> credentialsOptional = credentialsRepository.findByEmail(email);
        if (credentialsOptional.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "user does not exist.");
        RefreshToken refreshToken = RefreshToken.builder()
                .credentials(credentialsOptional.get())
                .token(UUID.randomUUID().toString())
                .expiryDate(Instant.now().plusMillis(600000)) // set expiry of refresh token to 10 minutes - you can configure it application.properties file
                .build();
        return refreshTokenRepository.save(refreshToken);
    }


    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    public void verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            throw new RuntimeException(token.getToken() + " Refresh token is expired. Please make a new login..!");
        }
    }

    public JWTResponse refreshToken(RefreshTokenRequestDTO refreshTokenRequestDTO) {
        Optional<RefreshToken> tokenOptional = findByToken(refreshTokenRequestDTO.getRefreshToken());
        if (tokenOptional.isEmpty())
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid refresh tokenOptional provided");

        RefreshToken token = tokenOptional.get();
        refreshTokenRepository.delete(token);
        verifyExpiration(token);

        Credentials userCredentials = token.getCredentials();
        RefreshToken refreshTokenObj = createRefreshToken(userCredentials.getEmail());

        String refreshToken = refreshTokenObj.getToken();
        String jwt = jwtUtils.generateTokenWithEmail(userCredentials.getEmail());

        return new JWTResponse(jwt, refreshToken);
    }
}
