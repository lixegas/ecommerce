package com.lixega.ecommerce.auth.service;

import com.lixega.ecommerce.auth.config.JWTUtils;
import com.lixega.ecommerce.auth.model.dto.request.ValidationTokenRequest;
import com.lixega.ecommerce.auth.model.dto.response.ValidationTokenResponse;
import com.lixega.ecommerce.auth.model.entity.User;
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

        Optional<User> userOptional = userRepository.findByEmail(subject);
        if(userOptional.isEmpty()) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);

        User user = userOptional.get();
        return new ValidationTokenResponse(user.getId(), user.getEmail());
    }
}
