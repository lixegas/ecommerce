package com.lixega.ecommerce.auth.service;

import com.lixega.ecommerce.auth.config.JWTUtils;
import com.lixega.ecommerce.auth.model.dto.request.UserRegistrationRequest;
import com.lixega.ecommerce.auth.model.mapper.UserMapper;
import com.lixega.ecommerce.auth.model.entity.User;
import com.lixega.ecommerce.auth.model.entity.RefreshToken;
import com.lixega.ecommerce.auth.model.dto.request.LoginRequest;
import com.lixega.ecommerce.auth.model.dto.response.JWTResponse;
import com.lixega.ecommerce.auth.model.dto.response.UserRegistrationResponse;
import com.lixega.ecommerce.auth.repository.UserRepository;
import com.lixega.ecommerce.sdk.core.model.dto.UserProfileCreationRequest;
import lombok.AllArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@AllArgsConstructor
public class AuthService {
    private final KafkaTemplate<String, UserProfileCreationRequest> userProfileCreationRequestKafkaTemplate;

    private final RefreshTokenService refreshTokenService;
    private final AuthenticationManager authenticationManager;

    private final UserRepository userRepository;

    private final JWTUtils jwtUtils;
    private final PasswordEncoder passwordEncoder;

    private final UserMapper userMapper;

    public JWTResponse login(LoginRequest loginRequest) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword());
        Authentication authentication = authenticationManager.authenticate(authenticationToken);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String accessToken = jwtUtils.generateTokenWithEmail(loginRequest.getEmail());

        RefreshToken refreshTokenObj = refreshTokenService.createRefreshToken(loginRequest.getEmail());
        String refreshToken = refreshTokenObj.getToken();

        return new JWTResponse(accessToken, refreshToken);
    }

    public UserRegistrationResponse register(UserRegistrationRequest request) {
        if (!isEmailValid(request.getEmail())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid email provided");
        }

        if(!isPasswordValid(request.getPassword())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password must be at least 6 characters long.");
        };

        if(checkForUserWithSameEmail(request.getEmail())){
            String errorMessage = String.format("User with email %s already exists", request.getEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, errorMessage);
        };

        request.setPassword(passwordEncoder.encode(request.getPassword()));
        User registeredUser = userRepository.save(userMapper.mapToUser(request));

        UserProfileCreationRequest userProfileCreationRequest = userMapper.mapToProfileCreationRequest(registeredUser, request);
        userProfileCreationRequestKafkaTemplate.send("ecommerce-user-creation", userProfileCreationRequest);

        return userMapper.mapToRegistrationResponse(registeredUser);
    }

    private boolean checkForUserWithSameEmail(String email){
        return userRepository.findByEmail(email).isPresent();
    }

    private boolean isEmailValid(String email){
        String regex = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);

        return matcher.matches();
    }

    private boolean isPasswordValid(String password){
        return !password.isBlank() && password.length() >= 6;
    }
}
