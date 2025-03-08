package com.shop28.dto.request;

import lombok.Getter;

@Getter
public class UserCreateRequest {
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String email;
    private Integer phone;
}
