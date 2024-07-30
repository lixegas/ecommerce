package com.lixega.ecommerce.auth.service;

import com.lixega.ecommerce.auth.config.JWTUtils;
import com.lixega.ecommerce.auth.model.dto.request.UserRegistrationRequest;
import com.lixega.ecommerce.auth.model.entity.*;
import com.lixega.ecommerce.auth.model.enumeration.UserRoleEnum;
import com.lixega.ecommerce.auth.model.mapper.UserMapper;
import com.lixega.ecommerce.auth.model.dto.request.LoginRequest;
import com.lixega.ecommerce.auth.model.dto.response.LoginResponse;
import com.lixega.ecommerce.auth.model.dto.response.UserRegistrationResponse;
import com.lixega.ecommerce.auth.repository.AccessRepository;
import com.lixega.ecommerce.auth.repository.RoleRepository;
import com.lixega.ecommerce.auth.repository.UserRepository;
import com.lixega.ecommerce.auth.repository.UserRoleRepository;
import com.lixega.ecommerce.sdk.core.model.dto.request.UserProfileCreationRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@AllArgsConstructor
public class AuthService {
    private final KafkaTemplate<String, UserProfileCreationRequest> userProfileCreationRequestKafkaTemplate;

    private final RefreshTokenService refreshTokenService;
    private final SessionService sessionService;

    private final AuthenticationManager authenticationManager;

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserRoleRepository userRoleRepository;
    private final AccessRepository accessRepository;

    private final PasswordEncoder passwordEncoder;

    private final UserMapper userMapper;

    public LoginResponse login(LoginRequest loginRequest, String ipAddress, HttpServletResponse response) {
        Authentication authentication = authenticateUser(loginRequest);
        if(authentication == null){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid email or password");
        }

        UserCredentials userCredentials = getUserCredentials(authentication);
        if(userCredentials == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find user");
        }

        Access access = accessRepository.save(buildAccess(userCredentials, ipAddress));
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(access);
        Session session = sessionService.createSession(access);

        access.setRefreshToken(refreshToken);
        accessRepository.save(access);

        addCookiesToResponse(response, session.getSessionUuid(), refreshToken.getToken());

        return new LoginResponse(userCredentials.getId());
    }

    private Authentication authenticateUser(LoginRequest loginRequest){
        try{
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword());
            return authenticationManager.authenticate(authenticationToken);
        }catch (Exception e){
            return null;
        }
    }

    private UserCredentials getUserCredentials(Authentication authentication){
        User user = (User) authentication.getPrincipal();
        Long userId = Long.valueOf(user.getUsername());

        Optional<UserCredentials> userCredentialsOptional = userRepository.findById(userId);
        return userCredentialsOptional.orElse(null);
    }

    private Access buildAccess(UserCredentials userCredentials, String ipAddress) {
        String accessUuid = UUID.randomUUID().toString();
        Access.AccessBuilder accessBuilder = Access.builder()
                .accessUuid(accessUuid)
                .ipAddress(ipAddress)
                .accessTimestamp(Instant.now())
                .userCredentials(userCredentials);

        return accessBuilder.build();
    }

    private void addCookiesToResponse(HttpServletResponse response, String sessionUuid, String refreshToken){
        Cookie sessionCookie = new Cookie("lixega.session", sessionUuid);
        sessionCookie.setSecure(true);
        sessionCookie.setHttpOnly(true);

        response.addCookie(sessionCookie);

        Cookie refreshCookie = new Cookie("lixega.refresh", refreshToken);
        refreshCookie.setSecure(true);
        refreshCookie.setHttpOnly(true);

        response.addCookie(refreshCookie);
    }

    public UserRegistrationResponse register(UserRegistrationRequest request) {
        if (doesUserWithSameEmailExist(request.getEmail())) {
            String errorMessage = String.format("User with email %s already exists", request.getEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, errorMessage);
        }

        request.setPassword(passwordEncoder.encode(request.getPassword()));
        UserCredentials registeredUserCredentials = userRepository.save(userMapper.mapToUser(request));

        Optional<Role> buyerRoleOptional = roleRepository.findById(UserRoleEnum.BUYER.getName());
        if (buyerRoleOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Missing roles. Contact your admin for further information");
        }

        Role buyerRole = buyerRoleOptional.get();
        UserRole userRole = UserRole.builder()
                .userCredentials(registeredUserCredentials)
                .role(buyerRole)
                .build();
        userRoleRepository.save(userRole);

        UserProfileCreationRequest userProfileCreationRequest = userMapper.mapToProfileCreationRequest(registeredUserCredentials, request);
        userProfileCreationRequestKafkaTemplate.send("ecommerce-user-creation", userProfileCreationRequest);

        return userMapper.mapToRegistrationResponse(registeredUserCredentials);
    }

    private boolean doesUserWithSameEmailExist(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    public void refresh(HttpServletRequest request, HttpServletResponse response){
        Cookie[] requestCookies = request.getCookies();

        String refreshToken = extractRefreshToken(requestCookies);
        if(refreshToken == null){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "No refresh token found");
        }

        Session session = refreshTokenService.refresh(refreshToken);

        addCookiesToResponse(response, session.getSessionUuid());
    }

    private void addCookiesToResponse(HttpServletResponse response, String sessionUuid){
        Cookie sessionCookie = new Cookie("lixega.session", sessionUuid);
        sessionCookie.setSecure(true);
        sessionCookie.setHttpOnly(true);

        response.addCookie(sessionCookie);
    }

    public String extractRefreshToken(Cookie[] cookies){
        for(Cookie cookie : cookies){
            if(Objects.equals(cookie.getName(), "lixega.refresh")){
                return cookie.getValue();
            }
        }

        return null;
    }
}
