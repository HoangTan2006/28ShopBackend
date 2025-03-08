package com.shop28.service;

import com.shop28.dto.request.ProductVariantRequest;
import com.shop28.dto.response.ProductVariantResponse;

import java.util.List;

public interface ProductVariantService {
    List<ProductVariantResponse> getProductVariantsByProductId(Integer productId, Integer pageNumber, Integer size);

    ProductVariantResponse createProductVariant(Integer productId, ProductVariantRequest productVariantRequest);

    ProductVariantResponse updateProductVariant(Integer id, ProductVariantRequest productVariantRequest);
}
