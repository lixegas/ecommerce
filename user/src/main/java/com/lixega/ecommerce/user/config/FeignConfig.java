package com.lixega.ecommerce.user.config;

import feign.Logger;
import feign.Retryer;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {

    @Bean
    public Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }

    @Bean
    public Retryer feignRetryer() {
        return new Retryer.Default();
    }

    @Bean
    public ErrorDecoder feignErrorDecoder() {
        return new ErrorDecoder.Default();
    }
}
