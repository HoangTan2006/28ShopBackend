package com.shop28.service;

import com.shop28.dto.request.UserCreateRequest;
import com.shop28.dto.request.UserUpdateRequest;
import com.shop28.dto.response.UserResponse;
import com.shop28.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService {
    User findByUsername(String username);

    List<UserResponse> getUsers(Integer pageNumber);

    UserResponse createUser(UserCreateRequest userCreateRequest);

    UserResponse updateUser(Integer id, UserUpdateRequest userUpdate);
}
