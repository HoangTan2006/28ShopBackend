package com.shop28.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import org.springframework.format.annotation.NumberFormat;

@Getter
public class ProductDetailRequest {
    @NotBlank(message = "Vui lòng nhập đường dẫn hình ảnh sản phẩm")
    private String imageUrl;

    @NotBlank(message = "Vui lòng chọn màu cho sản phẩm")
    private String color;

    @NotBlank(message = "Vui lòng chọn size cho sản phẩm")
    private String size;

    @NumberFormat
    @NotBlank(message = "Vui lòng nhập giá cho sản phẩm")
    private Integer price;

    @NumberFormat
    @NotBlank(message = "Vui lòng nhập số lượng tồn kho của sản phẩm")
    private Integer stockQuantity;
}
