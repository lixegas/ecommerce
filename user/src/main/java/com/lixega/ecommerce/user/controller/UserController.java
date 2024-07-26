package com.lixega.ecommerce.user.controller;


import com.lixega.ecommerce.user.model.entity.dto.request.UserDto;
import com.lixega.ecommerce.user.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/user")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    public UserDto getUser (@PathVariable Long id){
        return userService.getUserById(id);
    }
}
