package ir.maktab.service;

import ir.maktab.data.dto.AddressDto;
import ir.maktab.data.model.order.Address;


public interface AddressService {


    public AddressDto save(AddressDto address);

    public Address createAddress(String city, String street, int plaque);
}
