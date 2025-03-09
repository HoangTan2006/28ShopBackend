package com.shop28.service;

import com.shop28.dto.request.ProductDetailRequest;
import com.shop28.dto.response.ProductDetailResponse;

import java.util.List;

public interface ProductDetailService {
    List<ProductDetailResponse> getProductDetailsByProductId(Integer productId, Integer pageNumber, Integer size);

    ProductDetailResponse createProductDetail(Integer productId, ProductDetailRequest productDetailRequest);

    ProductDetailResponse updateProductDetail(Integer id, ProductDetailRequest productDetailRequest);
}
