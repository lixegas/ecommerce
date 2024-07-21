package com.lixega.ecommerce.auth.model.mapper;


import com.lixega.ecommerce.auth.model.entity.User;
import com.lixega.ecommerce.auth.model.dto.request.UserRegistrationRequest;
import com.lixega.ecommerce.auth.model.dto.response.UserRegistrationResponse;
import com.lixega.ecommerce.sdk.core.model.dto.UserProfileCreationRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

import java.time.Instant;

@Named("CredentialsMapper")
@Mapper(imports = Instant.class, componentModel = "spring")
public interface UserMapper {


    // Mappa RegistrationRequest a Credentials
    @Mapping(target = "createdAt", expression = "java(java.time.Instant.now())")
    User mapToUser(UserRegistrationRequest request);

    // Mappa Credentials a RegistrationResponse
    @Mapping(source = "createdAt", target = "createdAt")
    UserRegistrationResponse mapToRegistrationResponse(User user);

    @Mappings({
            @Mapping(target = "id", source = "user.id"),
            @Mapping(target = "firstName", source = "request.firstName"),
            @Mapping(target = "lastName", source = "request.lastName")
    })
    UserProfileCreationRequest mapToProfileCreationRequest(User user, UserRegistrationRequest request);
}

