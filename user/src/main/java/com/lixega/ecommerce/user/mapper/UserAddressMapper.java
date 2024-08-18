package com.lixega.ecommerce.user.mapper;

import com.lixega.ecommerce.user.model.dto.UserAddressDTO;
import com.lixega.ecommerce.user.model.dto.request.UserAddressCreationRequest;
import com.lixega.ecommerce.user.model.entity.UserAddress;
import com.lixega.ecommerce.user.model.entity.UserProfile;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

import java.time.Instant;
import java.util.List;

@Named("UserAddressMapper")
@Mapper(imports = Instant.class, componentModel = "spring")
public interface UserAddressMapper {

    UserAddress mapToEntity(UserAddressCreationRequest request, UserProfile userProfile);

    UserAddressDTO mapToDto(UserAddress userAddress);

    List<UserAddressDTO> mapToDtos(List<UserAddress> addresses);
}
