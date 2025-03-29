package com.shop28.service.impl;

import com.shop28.dto.request.CartItemRequest;
import com.shop28.dto.request.CartItemUpdateQuantityRequest;
import com.shop28.dto.response.CartItemResponse;
import com.shop28.entity.CartItem;
import com.shop28.entity.ProductDetail;
import com.shop28.entity.User;
import com.shop28.mapper.CartItemMapper;
import com.shop28.repository.CartItemRepository;
import com.shop28.repository.ProductDetailRepository;
import com.shop28.repository.UserRepository;
import com.shop28.service.CartItemService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CartItemServiceImpl implements CartItemService {

    private final CartItemRepository cartItemRepository;
    private final ProductDetailRepository productDetailRepository;
    private final UserRepository userRepository;
    private final CartItemMapper cartItemMapper;

    @Override
    @Cacheable(cacheNames = "cart", key = "#userId")
    public List<CartItemResponse> getCartByUserId(Integer userId) {

        List<CartItem> cartItems = cartItemRepository.findByUserId(userId);

        return cartItems.stream().map(cartItemMapper::toCartItemDTO).toList();
    }

    @Override
    @CacheEvict(cacheNames = "cart", key = "#userId")
    public CartItemResponse createCartItem(Integer userId, CartItemRequest cartItemRequest) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        ProductDetail productDetail = productDetailRepository.findById(cartItemRequest.getProductDetailId())
                .orElseThrow(() -> new EntityNotFoundException("Product detail not found"));

        //kiểm tra xem sản phẩm còn hàng hay không
        if (productDetail.getStockQuantity() < cartItemRequest.getQuantity())
            throw new RuntimeException("The product" + productDetail.getProduct().getName() + "is out of stock");

        CartItem cartItem = CartItem.builder()
                .user(user)
                .productDetail(productDetail)
                .quantity(cartItemRequest.getQuantity())
                .price(productDetail.getPrice() * cartItemRequest.getQuantity())
                .build();

        cartItem = cartItemRepository.save(cartItem);
        log.info("Created cart item ID: {} by user ID: {}", cartItem.getId(), userId);

        return cartItemMapper.toCartItemDTO(cartItem);
    }

    @Override
    @CacheEvict(cacheNames = "cart", key = "#userId")
    public CartItemResponse updateCartItem(Integer userId, Integer id, CartItemUpdateQuantityRequest cartItemRequest) {

        CartItem cartItem = cartItemRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cart item not found"));

        //xác thực cartitem cần cập nhật này chính là của user này
        if (!cartItem.getUser().getId().equals(userId)) throw new RuntimeException("Cannot be update");

        ProductDetail productDetail = productDetailRepository.findById(cartItem.getProductDetail().getId())
                .orElseThrow(() -> new EntityNotFoundException("Product detail not found"));

        //kiểm tra xem sản phẩm còn hàng hay không
        if (productDetail.getStockQuantity() < cartItemRequest.getQuantity())
            throw new RuntimeException("The product" + productDetail.getProduct().getName() + "is out of stock");

        cartItem.setQuantity(cartItemRequest.getQuantity());
        cartItem.setPrice(cartItemRequest.getQuantity() * productDetail.getPrice());

        cartItem = cartItemRepository.save(cartItem);
        log.info("Updated cart item ID: {} by user ID: {}", cartItem.getId(), userId);

        return cartItemMapper.toCartItemDTO(cartItem);
    }

    @Override
    @CacheEvict(cacheNames = "cart", key = "#userId")
    public void deleteCartItem(Integer userId, Integer cartItemId) {
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new EntityNotFoundException("Cart item not found"));

        //xác thực cartitem cần xóa là của user này
        if (!cartItem.getUser().getId().equals(userId)) throw new RuntimeException("Cannot be deleted");

        cartItemRepository.deleteById(cartItemId);
        log.info("Deleted cart item ID: {}", cartItemId);
    }
}
