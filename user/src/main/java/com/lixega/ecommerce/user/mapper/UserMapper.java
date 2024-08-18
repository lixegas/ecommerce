package com.lixega.ecommerce.user.mapper;

import com.lixega.ecommerce.user.model.dto.UserProfileDTO;
import com.lixega.ecommerce.user.model.entity.UserProfile;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

import java.time.Instant;

@Named("UserProfileMapper")
@Mapper(imports = Instant.class, componentModel = "spring")
public interface UserMapper {

    @Mappings({
        @Mapping(target = "id", source = "userId")
    })
    UserProfileDTO mapToUserProfileDTO(UserProfile userProfile);
}
