package com.lixega.ecommerce.order.service;


import com.lixega.ecommerce.order.client.PaymentClient;
import com.lixega.ecommerce.order.client.ShipmentClient;
import com.lixega.ecommerce.order.client.WarehouseClient;
import com.lixega.ecommerce.order.mapper.OrderMapper;
import com.lixega.ecommerce.order.model.dto.OrderDTO;
import com.lixega.ecommerce.order.model.entity.Order;
import com.lixega.ecommerce.order.model.entity.OrderItem;
import com.lixega.ecommerce.order.model.enumeration.OrderStatus;
import com.lixega.ecommerce.order.model.request.OrderRequest;
import com.lixega.ecommerce.order.repository.OrderItemRepository;
import com.lixega.ecommerce.order.repository.OrderRepository;
import com.lixega.ecommerce.sdk.core.model.dto.ShipmentDto;
import com.lixega.ecommerce.sdk.core.model.enumeration.ShipmentStatus;
import com.lixega.ecommerce.sdk.core.model.response.PaymentResponse;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final WarehouseClient warehouseClient;
    private final PaymentClient paymentClient;
    private final OrderMapper orderMapper;
    private final ShipmentClient shipmentClient;



    @Transactional
    public OrderDTO createOrder(OrderRequest orderRequest) {

        boolean available = warehouseClient.reserveStock(orderRequest.getItems());
        if (!available) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Stock not available");
        }


        Order order = new Order();
        order.setStatus(OrderStatus.PENDING);
        orderRepository.save(order);

        OrderItem orderItem = new OrderItem();
        order.setItems(orderRequest.getItems().stream()
                .map(item -> OrderItem.builder()
                        .productId(item.getProductId())
                        .quantity(item.getQuantity())
                        .build())
                .collect(Collectors.toList()));
        orderItemRepository.save(orderItem);


        PaymentResponse paymentResponse = paymentClient.processPayment(orderRequest.getPaymentInfo());
        if (!paymentResponse.isSuccessful()) {
            order.setStatus(OrderStatus.CANCELED);
            orderRepository.save(order);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Payment failed");
        }

        order.setStatus(OrderStatus.CONFIRMED);
        ShipmentDto shipmentDto = new ShipmentDto();
        shipmentDto.setOrder(order.getId());
        shipmentDto.setShipmentStatus(ShipmentStatus.PENDING);
        ShipmentDto shipmentCreationRequest = shipmentClient.createShipment(shipmentDto);



        return orderMapper.toOrderDTO(order, shipmentDto, shipmentCreationRequest);
    }
}