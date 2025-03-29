package com.shop28.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class UserUpdateRequest {
    @NotBlank(message = "First name không được để trống")
    private String firstName;

    private String lastName;

//    @Email(message = "Email không hợp lệ")
    private String email;

    @Pattern(regexp = "^0[0-9]{9}$", message = "Số điện thoại không hợp lệ")
    private String phone;
}
