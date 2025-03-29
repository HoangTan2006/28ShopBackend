package com.shop28.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class AuthenticationRequest {
    @NotBlank(message = "Vui lòng nhập username")
    private String username;

    @NotBlank(message = "Vui lòng nhập mật khẩu")
    private String password;
}
