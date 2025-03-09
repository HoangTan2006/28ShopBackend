package com.shop28.controller;

import com.shop28.dto.request.AuthenticationRequest;
import com.shop28.dto.request.CartItemRequest;
import com.shop28.dto.request.RefreshTokenRequest;
import com.shop28.dto.response.*;
import com.shop28.entity.CartItem;
import com.shop28.service.AuthenticationService;
import com.shop28.service.CartItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
}
