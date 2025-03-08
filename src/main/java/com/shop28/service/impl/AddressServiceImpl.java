package com.shop28.service.impl;

import com.shop28.dto.request.AddressRequest;
import com.shop28.dto.response.AddressResponse;
import com.shop28.entity.Address;
import com.shop28.mapper.AddressMapper;
import com.shop28.repository.AddressRepository;
import com.shop28.service.AddressService;
import com.shop28.util.TypeStatus;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;
    private final AddressMapper addressMapper;

    @Override
    public AddressResponse createAddress(AddressRequest addressRequest) {

        Address address = addressMapper.toEntity(addressRequest);

        address = addressRepository.save(address);

        return addressMapper.toDTO(address);
    }

    @Override
    public AddressResponse updateAddress(Integer id, AddressRequest addressRequest) {
        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Address not found"));

        if (!address.getOrder().getStatus().equals(TypeStatus.PENDING.name())) throw new RuntimeException("The order is being shipped");

        address.setNumber(addressRequest.getNumber());
        address.setStreet(addressRequest.getStreet());
        address.setWard(addressRequest.getWard());
        address.setDistrict(addressRequest.getDistrict());
        address.setCity(addressRequest.getCity());

        address = addressRepository.save(address);

        return addressMapper.toDTO(address);
    }
}
