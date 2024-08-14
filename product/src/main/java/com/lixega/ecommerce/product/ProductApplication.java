package com.lixega.ecommerce.product;

import com.lixega.ecommerce.sdk.oauth2.SdkOauth2Configuration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
@Import({SdkOauth2Configuration.class})
public class ProductApplication {
    public static void main(String[] args) {SpringApplication.run(ProductApplication.class, args);
    }
}
