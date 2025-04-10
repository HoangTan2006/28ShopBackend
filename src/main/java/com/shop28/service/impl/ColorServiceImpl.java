package com.shop28.service.impl;

import com.shop28.dto.request.ColorRequest;
import com.shop28.dto.response.ColorResponse;
import com.shop28.entity.Color;
import com.shop28.repository.ColorRepository;
import com.shop28.service.ColorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ColorServiceImpl implements ColorService {

    private final ColorRepository colorRepository;

    @Override
    public List<ColorResponse> getColors() {
        List<Color> colors = colorRepository.findAll();

        return colors.stream().map(
                color -> new ColorResponse(color.getId(), color.getName())).toList();
    }

    @Override
    public ColorResponse createColor(ColorRequest colorRequest) {

        Color color = colorRepository.save(new Color(colorRequest.getName().toUpperCase()));
        log.info("Created color \" {} \" by Admin", color.getName());

        return new ColorResponse(color.getId(), color.getName());
    }
}
