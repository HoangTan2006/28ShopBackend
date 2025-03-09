package com.shop28.controller;

import com.shop28.dto.request.ColorRequest;
import com.shop28.dto.response.ColorResponse;
import com.shop28.dto.response.ResponseData;
import com.shop28.service.ColorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<ResponseData<List<ColorResponse>>> getSizes() {
        List<ColorResponse> colors = colorService.getColors();

        ResponseData<List<ColorResponse>> responseData = ResponseData.<List<ColorResponse>>builder()
                .status(HttpStatus.OK.value())
                .message("Success")
                .data(colors)
                .build();

        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ResponseData<ColorResponse>> createColor(@RequestBody ColorRequest colorRequest) {
        ColorResponse color = colorService.createColor(colorRequest);

        ResponseData<ColorResponse> responseData = ResponseData.<ColorResponse>builder()
                .status(HttpStatus.CREATED.value())
                .message("Success")
                .data(color)
                .build();

        return new ResponseEntity<>(responseData, HttpStatus.CREATED);
    }
}
