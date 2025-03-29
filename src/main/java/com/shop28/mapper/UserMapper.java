package com.shop28.mapper;

import com.shop28.dto.response.UserResponse;
import com.shop28.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserResponse toUserDTO(User user);
}
