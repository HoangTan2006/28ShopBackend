package com.shop28.service.impl;

import com.shop28.dto.request.ColorRequest;
import com.shop28.dto.response.ColorResponse;
import com.shop28.entity.Color;
import com.shop28.repository.ColorRepository;
import com.shop28.service.ColorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ColorServiceImpl implements ColorService {

    private final ColorRepository colorRepository;

    @Override
    public List<ColorResponse> getColors() {
        List<Color> colors = colorRepository.findAll();

        return colors.stream().map(color -> ColorResponse.builder()
                .id(color.getId())
                .name(color.getName())
                .build()).toList();
    }

    @Override
    public ColorResponse createColor(ColorRequest colorRequest) {

        Color color = Color.builder()
                .name(colorRequest.getName().toUpperCase())
                .build();

        color = colorRepository.save(color);

        return ColorResponse.builder()
                .id(color.getId())
                .name(color.getName())
                .build();
    }
}
