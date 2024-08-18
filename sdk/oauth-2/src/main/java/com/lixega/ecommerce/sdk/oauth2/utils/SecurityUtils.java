package com.lixega.ecommerce.sdk.oauth2.utils;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;

public class SecurityUtils {
    public static boolean isAuthenticated() {
        return SecurityContextHolder.getContext().getAuthentication().isAuthenticated();
    }

    public static Jwt getCurrentUserPrincipal() {
        return (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public static String getCurrentUserId() {
        return getCurrentUserPrincipal().getSubject();
    }
}
