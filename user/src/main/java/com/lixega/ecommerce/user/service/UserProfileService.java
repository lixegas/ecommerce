package com.lixega.ecommerce.user.service;

import com.lixega.ecommerce.sdk.core.model.dto.UserProfileCreationRequest;
import com.lixega.ecommerce.user.mapper.UserProfileMapper;
import com.lixega.ecommerce.user.model.entity.UserProfile;
import com.lixega.ecommerce.user.repository.UserProfileRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserProfileService {
    private final UserProfileMapper userProfileMapper;
    private final UserProfileRepository userProfileRepository;

    public void createUserProfile(UserProfileCreationRequest request){
        UserProfile userRegistered = userProfileMapper.mapToUserProfile(request);
        userProfileRepository.save(userRegistered);
    }

}
