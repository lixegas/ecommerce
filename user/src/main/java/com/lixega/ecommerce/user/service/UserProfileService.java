package com.lixega.ecommerce.user.service;

import com.lixega.ecommerce.sdk.core.model.dto.UserProfileCreationRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserProfileService {

    public void createUserProfile(UserProfileCreationRequest request){
        System.out.println(request);
    }
}
