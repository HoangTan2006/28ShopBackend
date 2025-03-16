package com.shop28.service.impl;

import com.shop28.dto.request.ProductDetailRequest;
import com.shop28.dto.response.ProductDetailResponse;
import com.shop28.entity.Color;
import com.shop28.entity.Product;
import com.shop28.entity.ProductDetail;
import com.shop28.entity.Size;
import com.shop28.mapper.ProductDetailMapper;
import com.shop28.repository.ColorRepository;
import com.shop28.repository.ProductRepository;
import com.shop28.repository.ProductDetailRepository;
import com.shop28.repository.SizeRepository;
import com.shop28.service.ProductDetailService;
import jakarta.persistence.EntityExistsException;
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
public class ProductDetailServiceImpl implements ProductDetailService {

    private final ProductDetailRepository productDetailRepository;
    private final ProductRepository productRepository;
    private final ColorRepository colorRepository;
    private final SizeRepository sizeRepository;
    private final ProductDetailMapper productDetailMapper;

    @Override
    public List<ProductDetailResponse> getProductDetailsByProductId(Integer productId, Integer pageNumber, Integer size) {

        Pageable pageable = PageRequest.of(pageNumber, size);

        List<ProductDetail> productDetails = productDetailRepository.findByProductId(productId, pageable);

        return productDetails.stream().map(productDetailMapper::toDTO).toList();
    }

    @Override
    public ProductDetailResponse createProductDetail(Integer productId, ProductDetailRequest productDetailRequest) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));

        Color color = colorRepository.findByName(productDetailRequest.getColor().toUpperCase())
                .orElseThrow(() -> new EntityNotFoundException("Color not found"));

        Size size = sizeRepository.findByName(productDetailRequest.getSize().toUpperCase())
                .orElseThrow(() -> new EntityNotFoundException("Size not found"));

        //Kiểm tra xem đã tồn tại biến thể này chưa
        if (productDetailRepository.existsByProductIdAndColorAndSize(productId, color, size))
            throw new EntityExistsException("Product detail is existed");

        ProductDetail productDetail = ProductDetail.builder()
                .product(product)
                .imageUrl(productDetailRequest.getImageUrl())
                .color(color)
                .size(size)
                .price(productDetailRequest.getPrice())
                .soldQuantity(0)
                .stockQuantity(productDetailRequest.getStockQuantity())
                .build();

        productDetail = productDetailRepository.save(productDetail);
        log.info("Created product detail ID: {}", productDetail.getId());

        return productDetailMapper.toDTO(productDetail);
    }

    @Override
    public ProductDetailResponse updateProductDetail(Integer id, ProductDetailRequest productDetailRequest) {

        ProductDetail productDetail = productDetailRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product detail not found"));

        Color color = colorRepository.findByName(productDetailRequest.getColor().toUpperCase())
                .orElseThrow(() -> new EntityNotFoundException("Color not found"));

        Size size = sizeRepository.findByName(productDetailRequest.getSize().toUpperCase())
                .orElseThrow(() -> new EntityNotFoundException("Size not found"));

        productDetail.setImageUrl(productDetailRequest.getImageUrl());
        productDetail.setColor(color);
        productDetail.setSize(size);
        productDetail.setPrice(productDetailRequest.getPrice());

        productDetail = productDetailRepository.save(productDetail);
        log.info("Updated product detail ID: {}", productDetail.getId());

        return productDetailMapper.toDTO(productDetail);
    }
}
