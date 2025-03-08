package com.shop28.controller;

import com.shop28.dto.request.SizeRequest;
import com.shop28.dto.response.ResponseData;
import com.shop28.dto.response.SizeResponse;
import com.shop28.entity.Size;
import com.shop28.service.SizeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/size")
@RequiredArgsConstructor
@EnableMethodSecurity
public class SizeController {

    private final SizeService sizeService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseData<List<SizeResponse>> getSizes() {
        List<SizeResponse> sizes = sizeService.getSizes();

        return ResponseData.<List<SizeResponse>>builder()
                .status(HttpStatus.OK.value())
                .message("Success")
                .data(sizes)
                .build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseData<Integer> sizeCreate(@RequestBody SizeRequest sizeRequest) {
        SizeResponse size = sizeService.createSize(sizeRequest);

        return ResponseData.<Integer>builder()
                .status(HttpStatus.OK.value())
                .message("Success")
                .data(size.getId())
                .build();
    }
}
