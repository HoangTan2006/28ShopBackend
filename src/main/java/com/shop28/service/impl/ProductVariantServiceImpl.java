package com.shop28.service.impl;

import com.shop28.dto.request.ProductVariantRequest;
import com.shop28.dto.response.ProductVariantResponse;
import com.shop28.entity.Color;
import com.shop28.entity.Product;
import com.shop28.entity.ProductVariant;
import com.shop28.entity.Size;
import com.shop28.mapper.ProductVariantMapper;
import com.shop28.repository.ColorRepository;
import com.shop28.repository.ProductRepository;
import com.shop28.repository.ProductVariantRepository;
import com.shop28.repository.SizeRepository;
import com.shop28.service.ProductVariantService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductVariantServiceImpl implements ProductVariantService {

    private final ProductVariantRepository productVariantRepository;
    private final ProductRepository productRepository;
    private final ColorRepository colorRepository;
    private final SizeRepository sizeRepository;
    private final ProductVariantMapper productVariantMapper;

    @Override
    public List<ProductVariantResponse> getProductVariantsByProductId(Integer productId, Integer pageNumber, Integer size) {

        Pageable pageable = PageRequest.of(pageNumber, size);

        List<ProductVariant> productVariants = productVariantRepository.findByProductId(productId, pageable);

        return productVariants.stream().map(productVariantMapper::toDTO).toList();
    }

    @Override
    public ProductVariantResponse createProductVariant(Integer productId, ProductVariantRequest productVariantRequest) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));

        Color color = colorRepository.findByName(productVariantRequest.getColor().toUpperCase())
                .orElseThrow(() -> new EntityNotFoundException("Color not found"));

        Size size = sizeRepository.findByName(productVariantRequest.getSize().toUpperCase())
                .orElseThrow(() -> new EntityNotFoundException("Size not found"));

        ProductVariant productVariant = ProductVariant.builder()
                .product(product)
                .imageUrl(productVariantRequest.getImageUrl())
                .color(color)
                .size(size)
                .price(productVariantRequest.getPrice())
                .soldQuantity(0)
                .stockQuantity(productVariantRequest.getStockQuantity())
                .build();

        productVariant = productVariantRepository.save(productVariant);

        return productVariantMapper.toDTO(productVariant);
    }

    @Override
    public ProductVariantResponse updateProductVariant(Integer id, ProductVariantRequest productVariantRequest) {

        ProductVariant productVariant = productVariantRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("ProductVariant not found"));

        Color color = colorRepository.findByName(productVariantRequest.getColor().toUpperCase())
                .orElseThrow(() -> new EntityNotFoundException("Color not found"));

        Size size = sizeRepository.findByName(productVariantRequest.getSize().toUpperCase())
                .orElseThrow(() -> new EntityNotFoundException("Size not found"));

        productVariant.setImageUrl(productVariantRequest.getImageUrl());
        productVariant.setColor(color);
        productVariant.setSize(size);
        productVariant.setPrice(productVariantRequest.getPrice());

        productVariant = productVariantRepository.save(productVariant);

        return productVariantMapper.toDTO(productVariant);
    }
}
