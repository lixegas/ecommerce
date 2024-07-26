package com.lixega.ecommerce.auth.service;

import com.lixega.ecommerce.auth.model.dto.response.CredentialsResponse;
import com.lixega.ecommerce.auth.model.entity.UserCredentials;
import com.lixega.ecommerce.auth.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;


    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<UserCredentials> userCredentialsOptional = userRepository.findByEmail(email);
        if (userCredentialsOptional.isEmpty()) {
            String errorMessage = String.format("User with email %s not found", email);
            throw new UsernameNotFoundException(errorMessage);
        }

        UserCredentials userCredentialsAccount = userCredentialsOptional.get();
        return new User(userCredentialsAccount.getEmail(), userCredentialsAccount.getPassword(), new ArrayList<>());
    }

    public CredentialsResponse getCredentialsById(Long id){
        UserCredentials user = userRepository.getUserById(id);
        CredentialsResponse credentials = new CredentialsResponse();

        credentials.setEmail(user.getEmail());
        credentials.setPhoneNumber(user.getPhoneNumber());
        credentials.setCreatedAt(user.getCreatedAt());

        return credentials;
    }
}