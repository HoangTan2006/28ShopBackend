package com.shop28.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class ColorResponse {
    private Integer id;
    private String name;
}