package ir.maktab.service;

import ir.maktab.data.dto.AddressDto;
import ir.maktab.data.entity.order.Address;


public interface AddressService {


    AddressDto save(AddressDto address);

}
