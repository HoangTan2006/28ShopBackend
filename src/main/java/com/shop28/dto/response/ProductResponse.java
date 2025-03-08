package com.shop28.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class ProductResponse {
    private Integer id;
    private String name;
    private String category;
    private String description;
    private String imageUrl;
    private Float star;
    private Integer price;
}
