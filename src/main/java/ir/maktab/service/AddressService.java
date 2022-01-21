package ir.maktab.service;

import ir.maktab.data.model.order.Address;


public interface AddressService {


    public void save(Address address);

    public Address createAddress(String city, String street, int plaque);
}
