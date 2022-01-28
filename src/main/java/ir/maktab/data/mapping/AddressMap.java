package ir.maktab.data.mapping;

import ir.maktab.data.dto.AddressDto;
import ir.maktab.data.model.order.Address;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Data
@Component
@RequiredArgsConstructor
public class AddressMap {
    private ModelMapper mapper;

    public Address createAddress(AddressDto addressDto) {
        Address address = Address.builder()
                .city(addressDto.getCity())
                .plaque(addressDto.getPlaque())
                .street(addressDto.getStreet()).build();
        return address;
    }

    public AddressDto createAddressDto(Address address) {
        AddressDto addressDto = AddressDto.builder()
                .street(address.getStreet())
                .plaque(address.getPlaque())
                .city(address.getCity()).build();
        return addressDto;
    }
}
