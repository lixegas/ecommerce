package com.lixega.ecommerce.auth.service;

import com.lixega.ecommerce.sdk.core.model.dto.CredentialsDTO;
import com.lixega.ecommerce.auth.model.entity.UserCredentials;
import com.lixega.ecommerce.auth.model.mapper.CredentialsMapper;
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
public class SqlUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    private final CredentialsMapper credentialsMapper;

    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<UserCredentials> userCredentialsOptional = userRepository.findByEmail(email);
        if (userCredentialsOptional.isEmpty()) {
            String errorMessage = String.format("User with email %s not found", email);
            throw new UsernameNotFoundException(errorMessage);
        }

        UserCredentials userCredentialsAccount = userCredentialsOptional.get();
        return new User(Long.toString(userCredentialsAccount.getId()), userCredentialsAccount.getPassword(), new ArrayList<>());
    }

    public CredentialsDTO getCredentialsById(Long id){
        UserCredentials user = userRepository.getUserById(id);
        return credentialsMapper.mapToDto(user);
    }
}