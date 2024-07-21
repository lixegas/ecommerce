package com.lixega.ecommerce.auth.service;

import com.lixega.ecommerce.auth.model.entity.User;
import com.lixega.ecommerce.auth.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CredentialService implements UserDetailsService {

    private final UserRepository userRepository;


    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> userAccountOptional = userRepository.findByEmail(email);
        if (userAccountOptional.isEmpty()) {
            String errorMessage = String.format("User with email %s not found", email);
            throw new UsernameNotFoundException(errorMessage);
        }

        User userAccount = userAccountOptional.get();
        return new org.springframework.security.core.userdetails.User(userAccount.getEmail(), userAccount.getPassword(), new ArrayList<>());
    }
}