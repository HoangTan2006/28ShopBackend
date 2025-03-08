package com.shop28.controller;

import com.shop28.dto.request.CartItemRequest;
import com.shop28.dto.request.CartItemUpdateQuantityRequest;
import com.shop28.dto.response.CartItemResponse;
import com.shop28.dto.response.ResponseData;
import com.shop28.entity.CustomUserDetails;
import com.shop28.service.CartItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
@EnableMethodSecurity
public class CartItemController {

    private final CartItemService cartItemService;

    @PreAuthorize("hasRole('USER')")
    @GetMapping
    public ResponseData<List<CartItemResponse>> getCartItems(Authentication authentication) {

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        List<CartItemResponse> cartItems = cartItemService.getCartByUserId(userDetails.getId());

        return ResponseData.<List<CartItemResponse>>builder()
                .status(HttpStatus.OK.value())
                .message("Success")
                .data(cartItems)
                .build();
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping
    public ResponseData<CartItemResponse> createCartItem(
            Authentication authentication,
            @RequestBody CartItemRequest cartItemRequest) {

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        CartItemResponse cartItem = cartItemService.createCartItem(userDetails.getId(), cartItemRequest);

        return ResponseData.<CartItemResponse>builder()
                .status(HttpStatus.CREATED.value())
                .message("Success")
                .data(cartItem)
                .build();
    }

    @PreAuthorize("hasRole('USER')")
    @PatchMapping("{id}")
    public ResponseData<CartItemResponse> updateCartItem(
            Authentication authentication,
            @PathVariable("id") Integer cartItemId,
            @RequestBody CartItemUpdateQuantityRequest cartItemRequest) {

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        CartItemResponse cartItem = cartItemService.updateCartItem(userDetails.getId(), cartItemId, cartItemRequest);

        return ResponseData.<CartItemResponse>builder()
                .status(HttpStatus.OK.value())
                .message("Success")
                .data(cartItem)
                .build();
    }

    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/{id}")
    public ResponseData<String> deleteCartItem(
            Authentication authentication,
            @PathVariable("id") Integer cartItemId) {

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        cartItemService.deleteCartItem(userDetails.getId(), cartItemId);

        return ResponseData.<String>builder()
                .status(HttpStatus.NO_CONTENT.value())
                .message("Deleted successfully")
                .data("Deleted")
                .build();
    }
}
