package com.lixega.ecommerce.auth.model.enumeration;

import lombok.Getter;

@Getter
public enum UserRoleEnum {
    ADMIN("admin"),
    BUYER("buyer"),
    SELLER("seller");

    private final String name;

    UserRoleEnum(String name){
        this.name = name;
    }
}
