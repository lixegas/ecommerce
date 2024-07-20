package com.lixega.ecommerce.auth.service;

import com.lixega.ecommerce.auth.model.entity.Credentials;
import com.lixega.ecommerce.auth.repository.CredentialsRepository;
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
public class CredentialService implements UserDetailsService {

    private final CredentialsRepository credentialsRepository;


    public UserDetails loadUserByEmail(String email) throws UsernameNotFoundException {
        Optional<Credentials> userAccountOptional = credentialsRepository.findByEmail(email);
        if (userAccountOptional.isEmpty()) {
            String errorMessage = String.format("User with email %s not found", email);
            throw new UsernameNotFoundException(errorMessage);
        }

        Credentials userAccount = userAccountOptional.get();
        return new User(userAccount.getEmail(), userAccount.getPassword(), new ArrayList<>());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}