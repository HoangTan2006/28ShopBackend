package com.shop28.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

import java.util.List;

@Getter
public class OrderRequest {
    @NotBlank
    private List<OrderDetailRequest> orderDetailList;

    @NotBlank
    private AddressRequest address;
}
