package com.lixega.ecommerce.user.controller;


import com.lixega.ecommerce.user.model.dto.UserProfileDTO;
import com.lixega.ecommerce.user.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("api/v1/user")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{userId}")
    private UserProfileDTO getUserProfileById(@PathVariable String userId) {
        if (userId == null || userId.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User id is mandatory");
        }
        return userService.getUserProfileById(userId);
    }
}
