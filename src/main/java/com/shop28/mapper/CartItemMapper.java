package com.shop28.mapper;

import com.shop28.dto.response.CartItemResponse;
import com.shop28.entity.CartItem;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CartItemMapper {
    CartItemResponse toCartItemDTO(CartItem cartItem);
}
