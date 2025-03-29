package com.shop28.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import org.springframework.format.annotation.NumberFormat;

@Getter
public class ProductRequest {
    @NotBlank(message = "Tên sản phẩm không được để trống")
    private String name;

    @NumberFormat
    @NotBlank(message = "Vui lòng nhập id danh mục cho sản phẩm")
    private Integer categoryId;

    private String description;

    @NotBlank(message = "Vui lòng nhập đường dẫn hình ảnh sản phẩm")
    private String imageUrl;

    @NumberFormat
    @NotBlank(message = "Vui lòng nhập giá cho sản phẩm")
    private Integer price;
}
