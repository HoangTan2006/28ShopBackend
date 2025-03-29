package com.shop28.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class AddressRequest {
    @NotBlank
    private String number;

    @NotBlank
    private String street;

    @NotBlank
    private String ward;

    @NotBlank
    private String district;

    @NotBlank
    private String city;
}
