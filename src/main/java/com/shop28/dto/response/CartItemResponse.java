package com.shop28.dto.response;

import com.shop28.entity.ProductVariant;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class CartItemResponse {
    private Integer id;
    private Integer productVariantId;
    private String name;
    private Integer quantity;
    private Integer price;
}
