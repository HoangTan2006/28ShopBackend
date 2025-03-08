package com.shop28.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class AuthenticationResponse {
    private UserResponse userResponse;
    private String accessToken;
    private String refreshToken;
}
