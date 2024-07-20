package com.lixega.ecommerce.sdk.security.model.mapper;

import com.lixega.ecommerce.sdk.security.model.dto.response.JwtValidationResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

@Named("JwtMapper")
@Mapper(componentModel = "spring")
public interface JwtMapper {
    default Authentication toAuthentication(JwtValidationResponse jwtValidationResponse){
        return new UsernamePasswordAuthenticationToken(jwtValidationResponse.getEmail(), "");
    }
}
