package com.lixega.ecommerce.bff;

import com.lixega.ecommerce.sdk.security.model.SdkSecurityConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;
import org.springframework.security.access.SecurityConfig;

@EnableFeignClients
@Import({SdkSecurityConfig.class})
@SpringBootApplication
public class BffApplication {
	public static void main(String[] args) {
		SpringApplication.run(BffApplication.class, args);
	}
}
