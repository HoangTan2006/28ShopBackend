package com.shop28.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TypeCategory {
    AO(1),
    QUAN(2),
    GIAY(3);
    private final Integer value;
}
