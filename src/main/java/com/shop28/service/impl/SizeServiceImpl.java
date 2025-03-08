package com.shop28.service.impl;

import com.shop28.dto.request.SizeRequest;
import com.shop28.dto.response.SizeResponse;
import com.shop28.entity.Size;
import com.shop28.repository.SizeRepository;
import com.shop28.service.SizeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SizeServiceImpl implements SizeService {

    private final SizeRepository sizeRepository;

    @Override
    public List<SizeResponse> getSizes() {
        return sizeRepository.findAll().stream().map(size -> SizeResponse.builder()
                .name(size.getName())
                .build()).toList();
    }

    @Override
    public SizeResponse createSize(SizeRequest sizeRequest) {
        Size size = Size.builder()
                .name(sizeRequest.getName().toUpperCase())
                .build();

        size = sizeRepository.save(size);

        return SizeResponse.builder()
                .id(size.getId())
                .name(size.getName())
                .build();
    }
}
