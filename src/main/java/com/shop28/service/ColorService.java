package com.shop28.service;

import com.shop28.dto.request.ColorRequest;
import com.shop28.dto.response.ColorResponse;
import com.shop28.entity.Color;

import java.util.List;

public interface ColorService {
    List<ColorResponse> getColors();

    ColorResponse createColor(ColorRequest colorRequest);
}
