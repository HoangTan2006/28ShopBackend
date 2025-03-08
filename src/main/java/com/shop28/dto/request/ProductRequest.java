package com.shop28.dto.request;

import lombok.Getter;

@Getter
public class ProductRequest {
    private String name;
    private Integer categoryId;
    private String description;
    private String imageUrl;
    private Integer price;
}
