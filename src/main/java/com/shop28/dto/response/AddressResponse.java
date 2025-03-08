package com.shop28.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class AddressResponse {
    private Integer id;
    private String number;
    private String street;
    private String ward;
    private String district;
    private String city;
}
