package com.lixega.ecommerce.user.service;

import com.lixega.ecommerce.user.mapper.UserAddressMapper;
import com.lixega.ecommerce.user.mapper.UserMapper;
import com.lixega.ecommerce.user.model.dto.UserAddressDTO;
import com.lixega.ecommerce.user.model.dto.UserProfileDTO;
import com.lixega.ecommerce.user.model.dto.request.UserAddressCreationRequest;
import com.lixega.ecommerce.user.model.entity.UserAddress;
import com.lixega.ecommerce.user.model.entity.UserProfile;
import com.lixega.ecommerce.user.repository.UserAddressRepository;
import com.lixega.ecommerce.user.repository.UserProfileRepository;
import lombok.AllArgsConstructor;
//import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;

import static com.lixega.ecommerce.sdk.oauth2.utils.SecurityUtils.getCurrentUserId;

@Service
@AllArgsConstructor
public class UserService {

    private final UserProfileRepository userProfileRepository;
    private final UserAddressRepository userAddressRepository;

    private final UserMapper userMapper;
    private final UserAddressMapper userAddressMapper;

    public UserProfileDTO createUserProfile(String userId) {
        UserProfile userProfile = new UserProfile();
        userProfile.setUserId(userId);
        userProfile.setEnabled(true);

        UserProfile savedUserProfile = userProfileRepository.save(userProfile);

        return userMapper.mapToUserProfileDTO(savedUserProfile);
    }

    public UserProfileDTO getUserProfileById(String userId) {
        if (!Objects.equals(getCurrentUserId(), userId)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You are not allowed to see another user profile.");
        }

        UserProfile userProfile = userProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, STR."Can't find user preferences associated to id \{userId}"));

        return userMapper.mapToUserProfileDTO(userProfile);
    }

    public UserAddressDTO createUserAddress(String userId, UserAddressCreationRequest request) {
        String currentUserId = getCurrentUserId();
        if (!Objects.equals(userId, currentUserId)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You are not allowed to create addresses on behalf of another user.");
        }
        UserProfile userProfile = userProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, STR."Could not find profile associated to user \{userId}"));

        UserAddress userAddress = userAddressMapper.mapToEntity(request, userProfile);

        return userAddressMapper.mapToDto(userAddressRepository.save(userAddress));
    }

    public List<UserAddressDTO> getUserAddresses(String userId) {
        if (!Objects.equals(getCurrentUserId(), userId)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You are not allowed to see another user addresses.");
        }

        UserProfile userProfile = userProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, STR."Could not find profile associated to user \{userId}"));

        List<UserAddress> userAddresses = userAddressRepository.findByUserProfile(userProfile);

        return userAddressMapper.mapToDtos(userAddresses);
    }

    public UserAddressDTO getUserAddressById(String userId, Long addressId) {
        if (!Objects.equals(getCurrentUserId(), userId)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You are not allowed to see another user address.");
        }

        UserProfile userProfile = userProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, STR."Could not find profile associated to user \{userId}"));

        UserAddress userAddress = userAddressRepository.findByUserProfileAndId(userProfile, addressId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, STR."Could not find address with id \{addressId} for user \{userId}"));

        return userAddressMapper.mapToDto(userAddress);
    }

    public Long deleteAddressById(String userId, Long addressId){
        if (!Objects.equals(getCurrentUserId(), userId)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You are not allowed to see another user address.");
        }

        UserProfile userProfile = userProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, STR."Could not find profile associated to user \{userId}"));

        UserAddress userAddress = userAddressRepository.findByUserProfileAndId(userProfile, addressId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, STR."Could not find address with id \{addressId} for user \{userId}"));

        userAddressRepository.delete(userAddress);

        return addressId;
    }
}
