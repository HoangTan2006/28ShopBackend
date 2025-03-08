package com.shop28.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class OrderResponse {
    private Integer id;
    private Integer userId;
    private AddressResponse address;
    private Integer price;
    private String status;
}
