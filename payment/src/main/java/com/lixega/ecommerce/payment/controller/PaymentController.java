package com.lixega.ecommerce.payment.controller;

import com.lixega.ecommerce.sdk.core.model.dto.PaymentInfoDTO;
import com.lixega.ecommerce.sdk.core.model.response.PaymentResponse;
import com.lixega.ecommerce.payment.service.PaymentService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/payment")
@AllArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/process")
    public ResponseEntity<PaymentResponse> processPayment(@RequestBody PaymentInfoDTO paymentInfo) {
        PaymentResponse response = paymentService.processPayment(paymentInfo);
        return ResponseEntity.ok(response);
    }
}