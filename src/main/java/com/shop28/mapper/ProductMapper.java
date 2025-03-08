package com.shop28.mapper;

import com.shop28.dto.request.ProductRequest;
import com.shop28.dto.response.ProductResponse;
import com.shop28.entity.Product;
import org.springframework.stereotype.Service;

@Service
public class ProductMapper {
    public ProductResponse toDTO(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .category(product.getCategory().getName())
                .description(product.getDescription())
                .imageUrl(product.getImageUrl())
                .star(product.getStar())
                .price(product.getPrice())
                .build();
    }
}
