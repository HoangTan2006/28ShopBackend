package com.shop28.service.impl;

import com.shop28.dto.request.ProductRequest;
import com.shop28.dto.response.ProductResponse;
import com.shop28.entity.Category;
import com.shop28.entity.Product;
import com.shop28.mapper.ProductMapper;
import com.shop28.repository.CategoryRepository;
import com.shop28.repository.ProductRepository;
import com.shop28.service.ProductService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper productMapper;

    @Override
    public List<ProductResponse> getProducts(Integer pageNumber, Integer size) {
        Pageable pageable = PageRequest.of(pageNumber, size);
        List<Product> products = productRepository.findAll(pageable).getContent();
        log.info("Get list product from database");

        return products.stream().map(productMapper::toDTO).toList();
    }

    @Override
    public ProductResponse createProduct(ProductRequest productRequest) {

        Category category = categoryRepository.findById(productRequest.getCategoryId())
                .orElseThrow(() -> new EntityNotFoundException("Category not exist"));

        Product product = Product.builder()
                .name(productRequest.getName())
                .category(category)
                .description(productRequest.getDescription())
                .imageUrl(productRequest.getImageUrl())
                .star(0F)
                .price(productRequest.getPrice())
                .build();

        product = productRepository.save(product);
        log.info("Created product ID: {}", product.getId());

        return productMapper.toDTO(product);
    }

    @Override
    public ProductResponse updateProduct(Integer id, ProductRequest productRequest) {
        Product product = productRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Product not found"));

        product.setName(productRequest.getName());
        product.setDescription(productRequest.getDescription());
        product.setImageUrl(productRequest.getImageUrl());
        product.setPrice(productRequest.getPrice());

        product = productRepository.save(product);
        log.info("Updated product ID: {}", product.getId());

        return productMapper.toDTO(product);
    }
}
