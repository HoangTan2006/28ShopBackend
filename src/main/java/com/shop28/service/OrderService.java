package com.shop28.service;

import com.shop28.dto.request.AddressRequest;
import com.shop28.dto.request.OrderStatusUpdateRequest;
import com.shop28.dto.response.OrderResponse;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface OrderService {
    List<OrderResponse> getOrders(Integer pageNumber, Integer pageSize);

    List<OrderResponse> getOrdersByUserId(Integer userId, Integer pageNumber, Integer pageSize);

    OrderResponse createOrder(UserDetails user, AddressRequest addressRequest);

    OrderResponse updateStatusOder(Integer id, OrderStatusUpdateRequest orderStatusUpdateRequest);
}
