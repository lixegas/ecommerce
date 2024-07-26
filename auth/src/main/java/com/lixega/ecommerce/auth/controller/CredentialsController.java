package com.lixega.ecommerce.auth.controller;

import com.lixega.ecommerce.sdk.core.model.dto.CredentialsDTO;
import com.lixega.ecommerce.auth.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/credentials")
@AllArgsConstructor
public class CredentialsController {

    private final UserService userService;

    @GetMapping("/{id}")
    public CredentialsDTO credentials (@PathVariable Long id){
        return userService.getCredentialsById(id);
    }
}
