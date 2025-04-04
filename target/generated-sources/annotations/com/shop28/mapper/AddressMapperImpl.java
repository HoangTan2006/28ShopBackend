package com.shop28.mapper;

import com.shop28.dto.request.AddressRequest;
import com.shop28.dto.response.AddressResponse;
import com.shop28.entity.Address;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-04-04T19:52:51+0700",
    comments = "version: 1.6.3, compiler: javac, environment: Java 23.0.2 (Oracle Corporation)"
)
@Component
public class AddressMapperImpl implements AddressMapper {

    @Override
    public AddressResponse toAddressDTO(Address address) {
        if ( address == null ) {
            return null;
        }

        AddressResponse.AddressResponseBuilder addressResponse = AddressResponse.builder();

        addressResponse.id( address.getId() );
        addressResponse.number( address.getNumber() );
        addressResponse.street( address.getStreet() );
        addressResponse.ward( address.getWard() );
        addressResponse.district( address.getDistrict() );
        addressResponse.city( address.getCity() );

        return addressResponse.build();
    }

    @Override
    public Address toEntity(AddressRequest addressDTO) {
        if ( addressDTO == null ) {
            return null;
        }

        Address.AddressBuilder address = Address.builder();

        address.number( addressDTO.getNumber() );
        address.street( addressDTO.getStreet() );
        address.ward( addressDTO.getWard() );
        address.district( addressDTO.getDistrict() );
        address.city( addressDTO.getCity() );

        return address.build();
    }
}
