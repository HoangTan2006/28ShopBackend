package com.shop28.mapper;

import com.shop28.dto.response.ProductDetailResponse;
import com.shop28.entity.ProductDetail;
import org.springframework.stereotype.Service;

@Service
public class ProductDetailMapper {
    public ProductDetailResponse toDTO(ProductDetail productDetail) {
        return ProductDetailResponse.builder()
                .id(productDetail.getId())
                .name(productDetail.getProduct().getName())
                .category(productDetail.getProduct().getCategory().getName())
                .imageUrl(productDetail.getImageUrl())
                .color(productDetail.getColor().getName())
                .size(productDetail.getSize().getName())
                .price(productDetail.getPrice())
                .soldQuantity(productDetail.getSoldQuantity())
                .stockQuantity(productDetail.getStockQuantity())
                .build();
    }
}