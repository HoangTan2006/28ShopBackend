package com.shop28.controller;

import com.shop28.dto.request.AuthenticationRequest;
import com.shop28.dto.request.RefreshTokenRequest;
import com.shop28.dto.response.*;
import com.shop28.entity.CustomUserDetails;
import com.shop28.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping
    public ResponseEntity<ResponseData<AuthenticationResponse>> authentication(@RequestBody AuthenticationRequest authenticationRequest) {

        AuthenticationResponse authentication = authenticationService.authenticate(authenticationRequest);

        ResponseData<AuthenticationResponse> responseData = ResponseData.<AuthenticationResponse>builder()
                .status(HttpStatus.OK.value())
                .message("Success")
                .data(authentication)
                .build();

        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<ResponseData<AccessTokenResponse>> refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {

        AccessTokenResponse accessToken = authenticationService.verifyRefreshToken(refreshTokenRequest.getRefreshToken());

        ResponseData<AccessTokenResponse> responseData = ResponseData.<AccessTokenResponse>builder()
                .status(HttpStatus.OK.value())
                .message("Success")
                .data(accessToken)
                .build();

        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    @PostMapping("/log-out")
    public ResponseEntity<ResponseData<Void>> logOut(@RequestHeader("Authorization") String authorization) {
        authenticationService.logOut(authorization);

        ResponseData<Void> responseData = ResponseData.<Void>builder()
                .status(HttpStatus.NO_CONTENT.value())
                .message("Success")
                .build();

        return new ResponseEntity<>(responseData, HttpStatus.NO_CONTENT);
    }
}
