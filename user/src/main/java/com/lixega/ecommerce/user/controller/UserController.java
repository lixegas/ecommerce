package com.lixega.ecommerce.user.controller;


import com.lixega.ecommerce.user.model.dto.UserAddressDTO;
import com.lixega.ecommerce.user.model.dto.UserProfileDTO;
import com.lixega.ecommerce.user.model.dto.request.UserAddressCreationRequest;
import com.lixega.ecommerce.user.service.UserService;
import jakarta.annotation.Nonnull;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/user")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @PreAuthorize("hasRole('profile.view')")
    @GetMapping("/{userId}")
    public UserProfileDTO getUserProfileById(@PathVariable @Nonnull String userId) {
        return userService.getUserProfileById(userId);
    }

    @PreAuthorize("hasRole('address.create')")
    @PostMapping("/{userId}/address")
    public UserAddressDTO createUserAddress(@PathVariable String userId, @RequestBody UserAddressCreationRequest request) {
        return userService.createUserAddress(userId, request);
    }

    @PreAuthorize("hasRole('address.view')")
    @GetMapping("/{userId}/address")
    public List<UserAddressDTO> getUserAddresses(@PathVariable @Nonnull String userId) {
        return userService.getUserAddresses(userId);
    }

    @PreAuthorize("hasRole('address.view')")
    @GetMapping("/{userId}/address/{addressId}")
    public UserAddressDTO getUserAddressById(@PathVariable @Nonnull String userId, @PathVariable @Nonnull Long addressId) {
        return userService.getUserAddressById(userId, addressId);
    }

    @PreAuthorize("hasRole('address.delete'")
    @DeleteMapping("/{userId}/address/{addressId}")
    public Long deleteAddress(@PathVariable @Nonnull String userId, @PathVariable @Nonnull Long addressId){
        return userService.deleteAddressById(userId, addressId);
    }
}
