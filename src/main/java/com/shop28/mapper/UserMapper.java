package com.shop28.mapper;

import com.shop28.dto.response.UserResponse;
import com.shop28.entity.User;
import org.springframework.stereotype.Service;

@Service
public class UserMapper {

    public UserResponse toDTO(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .build();
    }
}
