package ir.maktab.data.mapping;

import ir.maktab.data.dto.AddressDto;
import ir.maktab.data.model.order.Address;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AddressMap {
    private ModelMapper mapper;

    public Address createAddress(AddressDto addressDto) {
        return mapper.map(addressDto, Address.class);
    }

    public AddressDto createAddressDto(Address address) {
        return mapper.map(address, AddressDto.class);
    }
}
