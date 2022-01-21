package ir.maktab.service;

import ir.maktab.data.dao.AddressDao;
import ir.maktab.data.model.order.Address;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Getter
@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final AddressDao addressDao;

    @Override
    public void save(Address address) {
        addressDao.save(address);
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
