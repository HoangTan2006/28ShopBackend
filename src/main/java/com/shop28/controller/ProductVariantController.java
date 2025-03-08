package com.shop28.controller;

import com.shop28.dto.request.ProductVariantRequest;
import com.shop28.dto.response.ProductVariantResponse;
import com.shop28.dto.response.ResponseData;
import com.shop28.service.ProductVariantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/productVariant")
@RequiredArgsConstructor
@EnableMethodSecurity
public class ProductVariantController {

    private final ProductVariantService productVariantService;

    @GetMapping
    public ResponseData<List<ProductVariantResponse>> getProductVariants(
            @RequestParam(value = "productId") Integer productId,
            @RequestParam(value = "pageNumber", defaultValue = "0") Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {

        List<ProductVariantResponse> productVariants = productVariantService.getProductVariantsByProductId(productId, pageNumber, pageSize);

        return ResponseData.<List<ProductVariantResponse>>builder()
                .status(HttpStatus.OK.value())
                .message("Success")
                .data(productVariants)
                .build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/{productId}")
    public ResponseData<Integer> createProductVariant(
            @PathVariable("productId") Integer productId,
            @RequestBody ProductVariantRequest productVariantRequest) {

        ProductVariantResponse productVariant = productVariantService.createProductVariant(productId, productVariantRequest);

        return ResponseData.<Integer>builder()
                .status(HttpStatus.CREATED.value())
                .message("Success")
                .data(productVariant.getId())
                .build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{id}")
    public ResponseData<Integer> updateProductVariant(
            @PathVariable("id") Integer id,
            @RequestBody ProductVariantRequest productVariantRequest) {

        ProductVariantResponse productVariant = productVariantService.updateProductVariant(id, productVariantRequest);

        return ResponseData.<Integer>builder()
                .status(HttpStatus.OK.value())
                .message("Success")
                .data(productVariant.getId())
                .build();
    }
}
