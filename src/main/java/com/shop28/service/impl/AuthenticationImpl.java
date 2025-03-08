package com.shop28.service.impl;

import com.shop28.dto.request.AuthenticationRequest;
import com.shop28.dto.response.AccessTokenResponse;
import com.shop28.dto.response.AuthenticationResponse;
import com.shop28.dto.response.UserResponse;
import com.shop28.entity.User;
import com.shop28.mapper.UserMapper;
import com.shop28.service.AuthenticationService;
import com.shop28.service.JwtService;
import com.shop28.service.UserService;
import com.shop28.util.TypeToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationImpl implements AuthenticationService {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserMapper userMapper;

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword()));

        User user = userService.findByUsername(authenticationRequest.getUsername());
        log.info("Authenticated userId {}", user.getId());

        String accessToken = jwtService.generateToken(user, TypeToken.ACCESS);
        String refreshToken = jwtService.generateToken(user, TypeToken.REFRESH);

        UserResponse userResponse = userMapper.toDTO(user);

        return AuthenticationResponse.builder()
                .userResponse(userResponse)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    public AccessTokenResponse verifyRefreshToken(String refreshToken) {
        String username = jwtService.verifyTokenAndExtractUserName(refreshToken, TypeToken.REFRESH);

        User user = userService.findByUsername(username);

        String accessToken = jwtService.generateToken(user, TypeToken.ACCESS);

        return AccessTokenResponse.builder()
                .accessToken(accessToken)
                .build();
    }


}
