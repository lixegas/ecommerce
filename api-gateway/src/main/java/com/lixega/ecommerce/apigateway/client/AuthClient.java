package com.lixega.ecommerce.apigateway.client;

import com.lixega.ecommerce.sdk.core.model.dto.request.JwtValidationRequest;
import com.lixega.ecommerce.sdk.core.model.dto.response.JwtValidationResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "${feign.auth.name}", url = "${feign.auth.url}")
public interface AuthClient {
    @RequestMapping(method = RequestMethod.POST, value = "api/v1/auth/validate")
    JwtValidationResponse validateJwt(@RequestBody JwtValidationRequest validationRequest);
}
