package com.lixega.ecommerce.auth.model.mapper;

import com.lixega.ecommerce.sdk.core.model.dto.CredentialsDTO;
import com.lixega.ecommerce.auth.model.entity.UserCredentials;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

import java.time.Instant;

@Named("CredentialsMapper")
@Mapper(imports = Instant.class, componentModel = "spring")
public interface CredentialsMapper {

    @Mappings({
                @Mapping(target = "id", source = "user.id"),
                @Mapping(target = "email", source = "user.email"),
                @Mapping(target = "phoneNumber", source = "user.phoneNumber"),
                @Mapping(target = "createdAt", source = "user.createdAt")
              })
    CredentialsDTO mapToDto(UserCredentials user);
}
