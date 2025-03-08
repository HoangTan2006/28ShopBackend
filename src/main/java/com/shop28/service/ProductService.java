package com.shop28.service;

import com.shop28.dto.request.ProductRequest;
import com.shop28.dto.response.ProductResponse;

import java.util.List;

public interface ProductService {
    List<ProductResponse> getProducts(Integer pageNumber, Integer size);

    ProductResponse createProduct(ProductRequest productRequest);

    ProductResponse updateProduct(Integer id, ProductRequest productRequest);
}
