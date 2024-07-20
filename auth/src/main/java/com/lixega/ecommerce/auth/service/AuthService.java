package com.lixega.ecommerce.auth.service;

import com.lixega.ecommerce.auth.config.JWTUtils;
import com.lixega.ecommerce.auth.model.mapper.CredentialsMapper;
import com.lixega.ecommerce.auth.model.entity.Credentials;
import com.lixega.ecommerce.auth.model.entity.RefreshToken;
import com.lixega.ecommerce.auth.model.dto.request.LoginRequest;
import com.lixega.ecommerce.auth.model.dto.request.RegistrationRequest;
import com.lixega.ecommerce.auth.model.dto.response.JWTResponse;
import com.lixega.ecommerce.auth.model.dto.response.RegistrationResponse;
import com.lixega.ecommerce.auth.repository.CredentialsRepository;
import lombok.AllArgsConstructor;

import org.springframework.http.HttpStatus;
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

    private final AuthenticationManager authenticationManager;
    private final CredentialsRepository credentialsRepository;
    private final CredentialsMapper credentialsMapper;
    private final PasswordEncoder passwordEncoder;
    private final RefreshTokenService refreshTokenService;
    private final JWTUtils jwtUtils;


    public JWTResponse login(LoginRequest loginRequest) {

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword());
        Authentication authentication = authenticationManager.authenticate(authenticationToken);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String accessToken = jwtUtils.generateTokenWithEmail(loginRequest.getEmail());

        RefreshToken refreshTokenObj = refreshTokenService.createRefreshToken(loginRequest.getEmail());
        String refreshToken = refreshTokenObj.getToken();

        return new JWTResponse(accessToken, refreshToken);
    }


    public RegistrationResponse register(RegistrationRequest request) {
        checkForValidEmail(request.getEmail());
        checkForValidPassword(request.getPassword());
        checkForUserWithSamePassword(request.getPassword());
        checkForUserWithSameEmail(request.getEmail());

        request.setPassword(passwordEncoder.encode(request.getPassword()));
        Credentials registeredCredentials = credentialsRepository.save(credentialsMapper.mapToCredentials(request));

        return credentialsMapper.mapToRegistrationResponse(registeredCredentials);
    }



    private void checkForUserWithSamePassword(String password){
        boolean userWithSameUsernameExists = credentialsRepository.findByPassword(password).isPresent();
        if(userWithSameUsernameExists) {
            String errorMessage = String.format("User with password %s already exists", password);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, errorMessage);
        }
    }

    private void checkForUserWithSameEmail(String email){
        boolean userWithSameUsernameExists = credentialsRepository.findByEmail(email).isPresent();
        if(userWithSameUsernameExists) {
            String errorMessage = String.format("User with email %s already exists", email);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, errorMessage);
        }
    }

    private void checkForValidEmail(String email){
        String regex = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);

        if(!matcher.matches()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid email provided");
        }
    }

    private void checkForValidPassword(String password){
        if(password.isBlank() || password.length() < 6){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password must be at least 6 characters long.");
        }
    }
}
