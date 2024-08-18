package com.lixega.ecommerce.user;

import com.lixega.ecommerce.sdk.oauth2.SdkOauth2Configuration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@SpringBootApplication
@Import({SdkOauth2Configuration.class})
public class UserApplication {
    public static void main(String[] args){
        SpringApplication.run(UserApplication.class, args);
    }
}
