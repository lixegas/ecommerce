package com.lixega.ecommerce.user.mapper;

import com.lixega.ecommerce.sdk.core.model.dto.UserProfileCreationRequest;
import com.lixega.ecommerce.user.model.entity.UserProfile;
import jdk.jfr.Name;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

import java.time.Instant;

@Named("UserProfileMapper")
@Mapper(imports = Instant.class, componentModel = "spring")
public interface UserProfileMapper {

    UserProfile mapToUserProfile (UserProfileCreationRequest request);
}
