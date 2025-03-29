package com.shop28.dto.request;

import lombok.Getter;
import org.springframework.format.annotation.NumberFormat;

@Getter
public class CartItemUpdateQuantityRequest {
    @NumberFormat
    private Integer quantity;
}
