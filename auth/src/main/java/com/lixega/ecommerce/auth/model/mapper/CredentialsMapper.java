package com.lixega.ecommerce.auth.model.mapper;


import com.lixega.ecommerce.auth.model.entity.User;
import com.lixega.ecommerce.auth.model.dto.request.RegistrationRequest;
import com.lixega.ecommerce.auth.model.dto.response.RegistrationResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.Instant;

@Named("CredentialsMapper")
@Mapper(imports = Instant.class, componentModel = "spring")
public interface CredentialsMapper {


    // Mappa RegistrationRequest a Credentials
    @Mapping(target = "createdAt", expression = "java(java.time.Instant.now())")
    User mapToCredentials(RegistrationRequest request);

    // Mappa Credentials a RegistrationResponse
    @Mapping(source = "createdAt", target = "createdAt")
    RegistrationResponse mapToRegistrationResponse(User user);
}

