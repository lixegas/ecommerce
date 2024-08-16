package com.lixega.ecommerce.warehouse.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductLocationRequest {

    private Integer quantity;
}
