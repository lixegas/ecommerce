package com.lixega.ecommerce.user.service;

import com.lixega.ecommerce.user.mapper.UserMapper;
import com.lixega.ecommerce.user.model.dto.UserProfileDTO;
import com.lixega.ecommerce.user.model.entity.UserProfile;
import com.lixega.ecommerce.user.repository.UserProfileRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {

    private final UserProfileRepository userProfileRepository;
    private final UserMapper userMapper;

    public UserProfileDTO createUserProfile(String userId){
        UserProfile userProfile = new UserProfile();
        userProfile.setUserId(userId);
        userProfile.setEnabled(true);

        UserProfile savedUserProfile = userProfileRepository.save(userProfile);

        return userMapper.mapToUserProfileDTO(savedUserProfile);
    }

    public UserProfileDTO getUserProfileById(String userId){
        Optional<UserProfile> userProfileOptional = userProfileRepository.findByUserId(userId);
        if(userProfileOptional.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, STR."Can't find user preferences associated to id \{userId}");
        }

        UserProfile userPreferences = userProfileOptional.get();

        return userMapper.mapToUserProfileDTO(userPreferences);
    }
}
