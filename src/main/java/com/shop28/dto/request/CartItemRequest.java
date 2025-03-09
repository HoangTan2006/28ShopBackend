package com.shop28.dto.request;

import lombok.Getter;

@Getter
public class CartItemRequest {
    private Integer productDetailId;
    private Integer quantity;
}
