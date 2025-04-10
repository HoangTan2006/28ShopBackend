package com.shop28.service.impl;

import com.shop28.dto.request.SizeRequest;
import com.shop28.dto.response.ColorResponse;
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
        List<Size> sizes = sizeRepository.findAll();
        return sizes.stream().map(size -> new SizeResponse(size.getId(), size.getName())).toList();
    }

    @Override
    public SizeResponse createSize(SizeRequest sizeRequest) {
        Size size = sizeRepository.save(new Size(sizeRequest.getName().toUpperCase()));

        return new SizeResponse(size.getId(), size.getName());
    }
}
