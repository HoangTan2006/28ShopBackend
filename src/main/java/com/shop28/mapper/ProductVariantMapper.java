package com.shop28.mapper;

import com.shop28.dto.response.ProductVariantResponse;
import com.shop28.entity.ProductVariant;
import org.springframework.stereotype.Service;

@Service
public class ProductVariantMapper {
    public ProductVariantResponse toDTO(ProductVariant productVariant) {
        return ProductVariantResponse.builder()
                .id(productVariant.getId())
                .name(productVariant.getProduct().getName())
                .category(productVariant.getProduct().getCategory().getName())
                .imageUrl(productVariant.getImageUrl())
                .color(productVariant.getColor().getName())
                .size(productVariant.getSize().getName())
                .price(productVariant.getPrice())
                .soldQuantity(productVariant.getSoldQuantity())
                .stockQuantity(productVariant.getStockQuantity())
                .build();
    }
}