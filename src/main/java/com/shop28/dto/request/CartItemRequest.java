package com.shop28.dto.request;

import com.shop28.entity.ProductVariant;
import lombok.Getter;

@Getter
public class CartItemRequest {
    private Integer productVariantId;
    private Integer quantity;
}
