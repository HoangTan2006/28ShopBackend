package com.shop28.service;

import com.shop28.dto.request.AddressRequest;
import com.shop28.dto.response.AddressResponse;

public interface AddressService {

    AddressResponse createAddress(AddressRequest addressRequest);

    AddressResponse updateAddress(Integer id, AddressRequest addressRequest);
}
