package com.lixega.ecommerce.user.mapper;

import com.lixega.ecommerce.user.model.dto.UserAddressDTO;
import com.lixega.ecommerce.user.model.entity.UserAddress;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

import java.time.Instant;
import java.util.List;

@Named("UserAddressMapper")
@Mapper(imports = Instant.class, componentModel = "spring")
public interface UserAddressMapper {
    UserAddressDTO mapToDto(UserAddress userAddress);

    List<UserAddressDTO> mapToDtos(List<UserAddress> addresses);
}
