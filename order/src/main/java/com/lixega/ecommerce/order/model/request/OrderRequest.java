package com.lixega.ecommerce.order.model.request;

import com.lixega.ecommerce.sdk.core.model.dto.OrderItemDTO;
import com.lixega.ecommerce.sdk.core.model.dto.PaymentInfoDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderRequest {

    private List<OrderItemDTO> items;
    private PaymentInfoDTO paymentInfo;
}