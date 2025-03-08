package com.shop28.dto.request;

import lombok.Getter;

@Getter
public class ProductVariantRequest {
    private String description;
    private String imageUrl;
    private String color;
    private String size;
    private Integer price;
    private Integer stockQuantity;
}
