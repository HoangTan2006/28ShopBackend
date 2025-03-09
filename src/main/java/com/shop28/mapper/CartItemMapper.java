package com.shop28.mapper;

import com.shop28.dto.response.CartItemResponse;
import com.shop28.entity.CartItem;
import org.springframework.stereotype.Service;

@Service
public class CartItemMapper {
    public CartItemResponse toDTO(CartItem cartItem) {
        return CartItemResponse.builder()
                .id(cartItem.getId())
                .productDetailId(cartItem.getProductDetail().getId())
                .name(cartItem.getProductDetail().getProduct().getName())
                .quantity(cartItem.getQuantity())
                .price(cartItem.getPrice())
                .build();
    }
}
