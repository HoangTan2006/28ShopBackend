package com.shop28.service;

import com.shop28.dto.request.CartItemRequest;
import com.shop28.dto.request.CartItemUpdateQuantityRequest;
import com.shop28.dto.response.CartItemResponse;

import java.util.List;

public interface CartItemService {
    List<CartItemResponse> getCartByUserId(Integer userId);

    CartItemResponse createCartItem(Integer userId, CartItemRequest cartItemRequest);

    CartItemResponse updateCartItem(Integer userId, Integer id, CartItemUpdateQuantityRequest cartItemRequest);

    void deleteCartItem(Integer userId, Integer cartItemId);
}
