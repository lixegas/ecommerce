package com.lixega.ecommerce.auth.service;

import com.lixega.ecommerce.auth.config.JWTUtils;
import com.lixega.ecommerce.auth.model.dto.request.ValidationTokenRequest;
import com.lixega.ecommerce.auth.model.dto.response.ValidationTokenResponse;
import com.lixega.ecommerce.auth.model.entity.UserCredentials;
import com.lixega.ecommerce.auth.repository.UserRepository;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ValidationTokenService {
    private final JWTUtils jwtUtils;
    private final UserRepository userRepository;

    public ValidationTokenResponse validateToken (ValidationTokenRequest request){

        String subject = jwtUtils.getSubject(request.getToken());
        if (subject == null) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);

        Optional<UserCredentials> credentialsOptional = userRepository.findByEmail(subject);
        if(credentialsOptional.isEmpty()) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);

        UserCredentials userCredentials = credentialsOptional.get();
        return new ValidationTokenResponse(userCredentials.getId(), userCredentials.getEmail());
    }
}
