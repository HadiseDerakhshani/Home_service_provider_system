package service;

import data.dao.AddressDao;
import data.model.order.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressService {
    AddressDao addressDao;

    @Autowired
    public AddressService(AddressDao addressDao) {
        this.addressDao = addressDao;
    }

    public void save(Address address) {
        addressDao.save(address);
    }

    public Address createAddress(String city, String street, int plaque) {
        Address address = Address.builder()
                .city(city)
                .street(street)
                .plaque(plaque)
                .build();
        return address;
    }
}
