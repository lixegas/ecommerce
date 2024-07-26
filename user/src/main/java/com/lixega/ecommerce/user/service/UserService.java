package com.lixega.ecommerce.user.service;


import com.lixega.ecommerce.sdk.core.model.dto.CredentialsDTO;
import com.lixega.ecommerce.sdk.core.model.dto.UserDto;
import com.lixega.ecommerce.user.client.UserClient;
import com.lixega.ecommerce.user.mapper.UserProfileMapper;
import com.lixega.ecommerce.user.model.entity.UserProfile;
import com.lixega.ecommerce.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserClient userClient;
    private final UserProfileMapper userProfileMapper;

    public UserDto getUserById(Long id) {
        CredentialsDTO userFromClient = userClient.getUserById(id);
        UserProfile userProfile = userRepository.getReferenceById(id);

        return userProfileMapper.mapToDTO(userProfile,userFromClient);
    }
}
