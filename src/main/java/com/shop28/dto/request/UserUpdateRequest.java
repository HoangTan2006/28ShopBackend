package com.shop28.dto.request;

import lombok.Getter;

@Getter
public class UserUpdateRequest {
    private String firstName;
    private String lastName;
    private String email;
    private Integer phone;
}
