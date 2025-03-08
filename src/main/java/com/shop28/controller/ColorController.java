package com.shop28.controller;

import com.shop28.dto.request.ColorRequest;
import com.shop28.dto.response.ColorResponse;
import com.shop28.dto.response.ResponseData;
import com.shop28.service.ColorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/color")
@RequiredArgsConstructor
@EnableMethodSecurity
public class ColorController {

    private final ColorService colorService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseData<List<ColorResponse>> getSizes() {
        List<ColorResponse> colors = colorService.getColors();

        return ResponseData.<List<ColorResponse>>builder()
                .status(HttpStatus.OK.value())
                .message("Success")
                .data(colors)
                .build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseData<Integer> createColor(@RequestBody ColorRequest colorRequest) {
        ColorResponse color = colorService.createColor(colorRequest);

        return ResponseData.<Integer>builder()
                .status(HttpStatus.OK.value())
                .message("Success")
                .data(color.getId())
                .build();
    }
}
