package com.lixega.ecommerce.sdk.core.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SessionClaims {
    private Long userId;
    private String userEmail;

    private List<RoleDTO> userRoles;
}
