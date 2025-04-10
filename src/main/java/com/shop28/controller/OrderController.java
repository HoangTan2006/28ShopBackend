package com.shop28.controller;

import com.shop28.dto.request.AddressRequest;
import com.shop28.dto.request.OrderRequest;
import com.shop28.dto.request.OrderStatusUpdateRequest;
import com.shop28.dto.response.OrderResponse;
import com.shop28.dto.response.ResponseData;
import com.shop28.entity.CustomUserDetails;
import com.shop28.entity.Order;
import com.shop28.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/order")
@RequiredArgsConstructor
@EnableMethodSecurity
public class OrderController {

    private final OrderService orderService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/list")
    public ResponseEntity<ResponseData<List<OrderResponse>>> getOrders(
            @RequestParam(value = "pageNumber", defaultValue = "0") Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {

        List<OrderResponse> orders = orderService.getOrders(pageNumber, pageSize);

        ResponseData<List<OrderResponse>> responseData = ResponseData.<List<OrderResponse>>builder()
                .status(HttpStatus.OK.value())
                .message("Success")
                .data(orders)
                .build();

        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping
    public ResponseEntity<ResponseData<List<OrderResponse>>> getOrdersByUserId(
            Authentication authentication,
            @RequestParam(value = "pageNumber", defaultValue = "0") Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        List<OrderResponse> orders = orderService.getOrdersByUserId(userDetails.getId(), pageNumber, pageSize);

        ResponseData<List<OrderResponse>> responseData = ResponseData.<List<OrderResponse>>builder()
                .status(HttpStatus.OK.value())
                .message("Success")
                .data(orders)
                .build();

        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping
    public ResponseEntity<ResponseData<OrderResponse>> createOrder(
            Authentication authentication,
            @RequestBody AddressRequest addressRequest) {

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        OrderResponse order = orderService.createOrder(userDetails, addressRequest);

        ResponseData<OrderResponse> responseData = ResponseData.<OrderResponse>builder()
                .status(HttpStatus.CREATED.value())
                .message("Success")
                .data(order)
                .build();

        return new ResponseEntity<>(responseData, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{id}")
    public ResponseEntity<ResponseData<OrderResponse>> updateStatusOrder(
            @PathVariable("id") Integer id,
            @RequestBody OrderStatusUpdateRequest orderStatusUpdateRequest) {

        OrderResponse order = orderService.updateStatusOder(id, orderStatusUpdateRequest);

        ResponseData<OrderResponse> responseData =  ResponseData.<OrderResponse>builder()
                .status(HttpStatus.OK.value())
                .message("Success")
                .data(order)
                .build();

        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

}
