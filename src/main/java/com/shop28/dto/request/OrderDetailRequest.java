package com.shop28.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import org.springframework.format.annotation.NumberFormat;

@Getter
public class OrderDetailRequest {
    @NumberFormat
    @NotBlank
    private Integer productDetailId;

    @NumberFormat
    @NotBlank
    private Integer quantity;
}
