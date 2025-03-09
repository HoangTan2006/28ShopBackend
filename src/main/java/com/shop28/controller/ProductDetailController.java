package com.shop28.controller;

import com.shop28.dto.request.ProductDetailRequest;
import com.shop28.dto.response.ProductDetailResponse;
import com.shop28.dto.response.ResponseData;
import com.shop28.service.ProductDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/product-detail")
@RequiredArgsConstructor
@EnableMethodSecurity
public class ProductDetailController {

    private final ProductDetailService productDetailService;

    @GetMapping
    public ResponseEntity<ResponseData<List<ProductDetailResponse>>> getProductDetails(
            @RequestParam(value = "productId") Integer productId,
            @RequestParam(value = "pageNumber", defaultValue = "0") Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {

        List<ProductDetailResponse> productDetails = productDetailService.getProductDetailsByProductId(productId, pageNumber, pageSize);

        ResponseData<List<ProductDetailResponse>> responseData = ResponseData.<List<ProductDetailResponse>>builder()
                .status(HttpStatus.OK.value())
                .message("Success")
                .data(productDetails)
                .build();

        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/{productId}")
    public ResponseEntity<ResponseData<ProductDetailResponse>> createProductDetail(
            @PathVariable("productId") Integer productId,
            @RequestBody ProductDetailRequest productDetailRequest) {

        ProductDetailResponse productDetail = productDetailService.createProductDetail(productId, productDetailRequest);

        ResponseData<ProductDetailResponse> responseData = ResponseData.<ProductDetailResponse>builder()
                .status(HttpStatus.CREATED.value())
                .message("Success")
                .data(productDetail)
                .build();

        return new ResponseEntity<>(responseData, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{id}")
    public ResponseEntity<ResponseData<ProductDetailResponse>> updateProductDetail(
            @PathVariable("id") Integer id,
            @RequestBody ProductDetailRequest productDetailRequest) {

        ProductDetailResponse productDetail = productDetailService.updateProductDetail(id, productDetailRequest);

        ResponseData<ProductDetailResponse> responseData = ResponseData.<ProductDetailResponse>builder()
                .status(HttpStatus.OK.value())
                .message("Success")
                .data(productDetail)
                .build();
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }
}
