package com.shop28.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class ProductVariantResponse {
    private Integer id;
    private String name;
    private String category;
    private String imageUrl;
    private String color;
    private String size;
    private Integer price;
    private Integer soldQuantity;
    private Integer stockQuantity;
}
