package com.shop28.service.impl;

import com.shop28.dto.request.AuthenticationRequest;
import com.shop28.dto.response.AccessTokenResponse;
import com.shop28.dto.response.AuthenticationResponse;
import com.shop28.dto.response.UserResponse;
import com.shop28.entity.Token;
import com.shop28.entity.User;
import com.shop28.mapper.UserMapper;
import com.shop28.repository.TokenRepository;
import com.shop28.service.*;
import com.shop28.util.TypeToken;
import io.jsonwebtoken.JwtException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserMapper userMapper;
    private final TokenRepository tokenRepository;
    private final TokenService tokenService;
    private final RedisBlacklistService blacklist;

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword()));

        User user = userService.findByUsername(authenticationRequest.getUsername());

        String accessToken = jwtService.generateToken(user, TypeToken.ACCESS);
        String refreshToken = jwtService.generateToken(user, TypeToken.REFRESH);

        Token token = Token.builder()
                .userId(user.getId())
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();

        //Lưu token vào database
        tokenRepository.save(token);

        UserResponse userResponse = userMapper.toDTO(user);
        log.info("User ID: {} authenticated", user.getId());

        return AuthenticationResponse.builder()
                .userResponse(userResponse)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    public AccessTokenResponse verifyRefreshToken(String refreshToken) {
        //Xác thực refresh token
        String username = jwtService.verifyTokenAndExtractUserName(refreshToken, TypeToken.REFRESH);

        if (!tokenRepository.existsByRefreshToken(refreshToken)) throw new JwtException("Invalid token");

        User user = userService.findByUsername(username);

        //tạo access token
        String accessToken = jwtService.generateToken(user, TypeToken.ACCESS);

        //Xóa access token cũ, cập nhật access token mới
        Token token = tokenService.updateToken(accessToken, refreshToken);
        log.info("User ID: {} verified the refresh token", user.getId());

        return AccessTokenResponse.builder()
                .accessToken(token.getAccessToken())
                .build();
    }

    @Override
    @Transactional
    public void logOut(String authorization) {
        String accessToken = authorization.replace("Bearer ", "");

        //Lưu access token vào blacklist
        blacklist.saveTokenToBlacklist(accessToken, "invalid", 60, TimeUnit.MINUTES);

        //Xóa access token và refresh token trong database
        tokenRepository.deleteByAccessToken(accessToken);

    }
}
