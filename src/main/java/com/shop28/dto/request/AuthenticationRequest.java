package com.shop28.dto.request;

import lombok.Getter;

@Getter
public class AuthenticationRequest {
    private String username;
    private String password;
}
