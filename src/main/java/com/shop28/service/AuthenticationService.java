package com.shop28.service;

import com.shop28.dto.request.AuthenticationRequest;
import com.shop28.dto.response.AccessTokenResponse;
import com.shop28.dto.response.AuthenticationResponse;

public interface AuthenticationService {
    AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest);

    AccessTokenResponse verifyRefreshToken(String refreshToken);
}
