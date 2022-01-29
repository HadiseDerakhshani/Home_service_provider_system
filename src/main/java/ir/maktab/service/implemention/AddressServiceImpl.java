package ir.maktab.service.implemention;

import ir.maktab.data.repasitory.AddressRepository;
import ir.maktab.data.dto.AddressDto;
import ir.maktab.data.mapping.AddressMap;
import ir.maktab.data.model.order.Address;
import ir.maktab.service.AddressService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Getter
@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;
    @Lazy
    private final AddressMap addressMap;

    @Override
    public AddressDto save(AddressDto address) {

        return addressMap.createAddressDto(addressRepository.save(addressMap.createAddress(address)));
    }

    @Override
    public Address createAddress(String city, String street, int plaque) {
        Address address = Address.builder()
                .city(city)
                .street(street)
                .plaque(plaque)
                .build();
        return address;
    }
}
