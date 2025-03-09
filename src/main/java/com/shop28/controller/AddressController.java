package com.shop28.controller;

import com.shop28.dto.request.AddressRequest;
import com.shop28.dto.response.AddressResponse;
import com.shop28.dto.response.ResponseData;
import com.shop28.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/address")
@RequiredArgsConstructor
@EnableMethodSecurity
public class AddressController {

    private final AddressService addressService;

    @PreAuthorize("hasRole('USER')")
    @PostMapping
    public ResponseEntity<ResponseData<AddressResponse>> createAddress(@RequestBody AddressRequest addressRequest) {

        AddressResponse addressResponse = addressService.createAddress(addressRequest);

        ResponseData<AddressResponse> responseData = ResponseData.<AddressResponse>builder()
                .status(HttpStatus.CREATED.value())
                .message("Success")
                .data(addressResponse)
                .build();

        return new ResponseEntity<>(responseData, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('USER')")
    @PatchMapping("/{id}")
    public ResponseEntity<ResponseData<AddressResponse>> updateAddress(
            @PathVariable("id") Integer id,
            @RequestBody AddressRequest addressRequest) {

        AddressResponse address = addressService.updateAddress(id, addressRequest);

        ResponseData<AddressResponse> responseData = ResponseData.<AddressResponse>builder()
                .status(HttpStatus.OK.value())
                .message("Success")
                .data(address)
                .build();

        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }
}
