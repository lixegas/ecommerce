package com.lixega.ecommerce.sdk.core.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentInfoDTO {

    private String cardNumber;
    private String cardHolderName;
    private String expirationDate;
    private String cvv;

}