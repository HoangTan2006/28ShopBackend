package com.shop28.service.impl;

import com.shop28.dto.request.CartItemRequest;
import com.shop28.dto.request.CartItemUpdateQuantityRequest;
import com.shop28.dto.response.CartItemResponse;
import com.shop28.entity.CartItem;
import com.shop28.entity.ProductVariant;
import com.shop28.entity.User;
import com.shop28.mapper.CartItemMapper;
import com.shop28.repository.CartItemRepository;
import com.shop28.repository.ProductVariantRepository;
import com.shop28.repository.UserRepository;
import com.shop28.service.CartItemService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CartItemServiceImpl implements CartItemService {

    private final CartItemRepository cartItemRepository;
    private final ProductVariantRepository productVariantRepository;
    private final UserRepository userRepository;
    private final CartItemMapper cartItemMapper;

    @Override
    public List<CartItemResponse> getCartByUserId(Integer userId) {

        List<CartItem> cartItems = cartItemRepository.findByUserId(userId);

        return cartItems.stream().map(cartItemMapper::toDTO).toList();
    }

    @Override
    public CartItemResponse createCartItem(Integer userId, CartItemRequest cartItemRequest) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        ProductVariant productVariant = productVariantRepository.findById(cartItemRequest.getProductVariantId())
                .orElseThrow(() -> new EntityNotFoundException("ProductVariant not found"));

        if (productVariant.getStockQuantity() < cartItemRequest.getQuantity())
            throw new RuntimeException("The product" + productVariant.getProduct().getName() + "is out of stock");
        //kiểm tra xem sản phẩm còn hàng hay không

        CartItem cartItem = CartItem.builder()
                .user(user)
                .productVariant(productVariant)
                .quantity(cartItemRequest.getQuantity())
                .price(productVariant.getPrice() * cartItemRequest.getQuantity())
                .build();

        cartItem = cartItemRepository.save(cartItem);
        log.info("Created cartItemId {} by userId {}", cartItem.getId(), userId);

        return cartItemMapper.toDTO(cartItem);
    }

    @Override
    public CartItemResponse updateCartItem(Integer userId, Integer id, CartItemUpdateQuantityRequest cartItemRequest) {

        CartItem cartItem = cartItemRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("CartItem not found"));

        if (!cartItem.getUser().getId().equals(userId)) throw new RuntimeException("Cannot be update");
        //xác thực cartitem cần cập nhật này chính là của user này

        ProductVariant productVariant = productVariantRepository.findById(cartItem.getProductVariant().getId())
                .orElseThrow(() -> new EntityNotFoundException("ProductVariant not found"));

        if (productVariant.getStockQuantity() < cartItemRequest.getQuantity())
            throw new RuntimeException("The product" + productVariant.getProduct().getName() + "is out of stock");
        //kiểm tra xem sản phẩm còn hàng hay không

        cartItem.setQuantity(cartItemRequest.getQuantity());
        cartItem.setPrice(cartItemRequest.getQuantity() * productVariant.getPrice());

        cartItem = cartItemRepository.save(cartItem);
        log.info("Updated cartItemId {} by userId {}", cartItem.getId(), userId);

        return cartItemMapper.toDTO(cartItem);
    }

    @Override
    public void deleteCartItem(Integer userId, Integer cartItemId) {
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new EntityNotFoundException("Cart item not found"));

        if (!cartItem.getUser().getId().equals(userId)) throw new RuntimeException("Cannot be deleted");
        //xác thực cartitem cần xóa là của user này

        cartItemRepository.deleteById(cartItemId);
        log.info("Deleted cart item {}", cartItemId);
    }
}
