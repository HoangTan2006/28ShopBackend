package com.shop28.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
@AllArgsConstructor
public class ColorResponse {
    private Integer id;
    private String name;

    public ColorResponse(String name) {
        this.name = name;
    }
}