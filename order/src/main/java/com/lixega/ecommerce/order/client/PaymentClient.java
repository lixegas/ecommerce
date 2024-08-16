package com.lixega.ecommerce.order.client;

import com.lixega.ecommerce.sdk.core.model.dto.PaymentInfoDTO;
import com.lixega.ecommerce.sdk.core.model.response.PaymentResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "payment-service")
public interface PaymentClient {

    @PostMapping("/process")
    PaymentResponse processPayment(@RequestBody PaymentInfoDTO paymentInfo);
}