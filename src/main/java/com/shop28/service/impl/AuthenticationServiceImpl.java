package com.shop28.service.impl;

import com.shop28.dto.request.AuthenticationRequest;
import com.shop28.dto.response.AuthenticationResponse;
import com.shop28.dto.response.UserResponse;
import com.shop28.entity.Token;
import com.shop28.entity.User;
import com.shop28.mapper.UserMapper;
import com.shop28.repository.TokenRepository;
import com.shop28.service.*;
import com.shop28.util.TypeToken;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.micrometer.common.util.StringUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.UUID;
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
    private final RedisBlacklistService blacklist;

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword()));

        User user = userService.findByUsername(authenticationRequest.getUsername());

        String tokenId = UUID.randomUUID().toString();
        String accessToken = jwtService.generateToken(user, TypeToken.ACCESS, tokenId);
        String refreshToken = jwtService.generateToken(user, TypeToken.REFRESH, tokenId);

        UserResponse userResponse = userMapper.toDTO(user);
        log.info("User ID: {} authenticated", user.getId());

        return AuthenticationResponse.builder()
                .userResponse(userResponse)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    public AuthenticationResponse verifyRefreshToken(String refreshToken) {
        if (StringUtils.isBlank(refreshToken)) throw new RuntimeException("token must not be blank");
        //Xác thực refresh token
        Claims extractToken = jwtService.verifyToken(refreshToken, TypeToken.REFRESH);
        String username = extractToken.getSubject();

        if (!tokenRepository.existsByRefreshToken(refreshToken)) throw new JwtException("Invalid token");

        User user = userService.findByUsername(username);

        //tạo access token
        String accessToken = jwtService.generateToken(user, TypeToken.ACCESS, extractToken.getId());
        log.info("User ID: {} verified the refresh token", user.getId());

        return AuthenticationResponse.builder()
                .userResponse(userMapper.toDTO(user))
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    @Transactional
    public void logOut(String tokenId, String accessToken) {
        accessToken = accessToken.replace("Bearer ", "");

        //Lưu access token vào blacklist
        blacklist.saveTokenToBlacklist(accessToken, "invalid", 60, TimeUnit.MINUTES);

        //Xóa access token và refresh token trong database
        tokenRepository.deleteById(tokenId);
    }
}
