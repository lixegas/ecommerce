package com.lixega.ecommerce.user.client;




import com.lixega.ecommerce.sdk.core.model.dto.CredentialsDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-client")
public interface UserClient {

    @GetMapping("/{id}")
    CredentialsDTO getUserById(@PathVariable("id") Long id);
}
