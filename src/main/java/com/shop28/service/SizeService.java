package com.shop28.service;

import com.shop28.dto.request.SizeRequest;
import com.shop28.dto.response.SizeResponse;

import java.util.List;

public interface SizeService {
    List<SizeResponse> getSizes();

    SizeResponse createSize(SizeRequest sizeRequest);
}
