package com.shop28.controller;

import com.shop28.dto.request.UserCreateRequest;
import com.shop28.dto.request.UserUpdateRequest;
import com.shop28.dto.response.ResponseData;
import com.shop28.dto.response.UserResponse;
import com.shop28.entity.CustomUserDetails;
import com.shop28.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
@EnableMethodSecurity
public class UserController {

    private final UserService userService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/list")
    public ResponseEntity<ResponseData<List<UserResponse>>> getUsers(
            @RequestParam(value = "page", defaultValue = "0") Integer pageNumber) {

        List<UserResponse> users = userService.getUsers(pageNumber);

        ResponseData<List<UserResponse>> responseData = ResponseData.<List<UserResponse>>builder()
                .status(HttpStatus.OK.value())
                .message("Success")
                .data(users)
                .build();

        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ResponseData<UserResponse>> createUser(@RequestBody UserCreateRequest userCreate) {
        UserResponse user = userService.createUser(userCreate);

        ResponseData<UserResponse> responseData = ResponseData.<UserResponse>builder()
                .status(HttpStatus.CREATED.value())
                .message("Created successful")
                .data(user)
                .build();

        return new ResponseEntity<>(responseData, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('USER')")
    @PatchMapping
    public ResponseEntity<ResponseData<UserResponse>> updateUser(Authentication authentication, @RequestBody UserUpdateRequest userUpdate) {

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        UserResponse userResponse = userService.updateUser(userDetails.getId(), userUpdate);

        ResponseData<UserResponse> responseData = ResponseData.<UserResponse>builder()
                .status(HttpStatus.OK.value())
                .message("Updated successful")
                .data(userResponse)
                .build();

        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }
}
