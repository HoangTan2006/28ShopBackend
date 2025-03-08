package com.shop28.service;

import com.shop28.dto.request.AddressRequest;
import com.shop28.dto.request.OrderStatusUpdateRequest;
import com.shop28.dto.response.OrderResponse;

import java.util.List;

public interface OrderService {
    List<OrderResponse> getOrders(Integer pageNumber, Integer pageSize);

    List<OrderResponse> getOrdersByUserId(Integer userId, Integer pageNumber, Integer pageSize);

    OrderResponse createOrder(Integer userId, AddressRequest addressRequest);

    OrderResponse updateStatusOder(Integer id, OrderStatusUpdateRequest orderStatusUpdateRequest);
}
