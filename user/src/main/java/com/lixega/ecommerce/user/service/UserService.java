package com.lixega.ecommerce.user.service;

import com.lixega.ecommerce.user.client.UserClient;
import com.lixega.ecommerce.user.model.entity.UserProfile;
import com.lixega.ecommerce.user.model.entity.dto.request.UserDto;
import com.lixega.ecommerce.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserClient userClient;

    public UserDto getUserById(Long id) {
        UserDto userFromClient = userClient.getUserById(id);
        UserProfile userFromRepository = userRepository.findUserById(id);

        UserDto user = new UserDto();
        user.setEmail(userFromClient.getEmail());
        user.setFirstName(userFromRepository.getFirstName());
        user.setLastName(userFromRepository.getLastName());
        user.setPhoneNumber(userFromClient.getPhoneNumber());

        return user;
    }
}
