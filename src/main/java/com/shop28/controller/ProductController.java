package com.shop28.controller;

import com.shop28.dto.request.ProductRequest;
import com.shop28.dto.response.ProductResponse;
import com.shop28.dto.response.ResponseData;
import com.shop28.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
@EnableMethodSecurity
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<ResponseData<List<ProductResponse>>> getProducts(
            @RequestParam(value = "pageNumber", defaultValue = "0") Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {

        List<ProductResponse> products = productService.getProducts(pageNumber, pageSize);

        ResponseData<List<ProductResponse>> responseData = ResponseData.<List<ProductResponse>>builder()
                .status(HttpStatus.OK.value())
                .message("Success")
                .data(products)
                .build();

        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ResponseData<ProductResponse>> createProduct(@RequestBody ProductRequest productRequest) {
        ProductResponse product = productService.createProduct(productRequest);

        ResponseData<ProductResponse> responseData = ResponseData.<ProductResponse>builder()
                .status(HttpStatus.CREATED.value())
                .message("Success")
                .data(product)
                .build();

        return new ResponseEntity<>(responseData, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{id}")
    public ResponseEntity<ResponseData<ProductResponse>> updateProduct(
            @PathVariable("id") Integer id,
            @RequestBody ProductRequest productRequest) {

        ProductResponse product = productService.updateProduct(id, productRequest);

        ResponseData<ProductResponse> responseData = ResponseData.<ProductResponse>builder()
                .status(HttpStatus.OK.value())
                .message("Success")
                .data(product)
                .build();

        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }
}
