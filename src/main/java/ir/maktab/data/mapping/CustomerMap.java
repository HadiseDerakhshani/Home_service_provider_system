package ir.maktab.data.mapping;

import ir.maktab.data.dto.CustomerDto;
import ir.maktab.data.model.user.Customer;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomerMap {
    private ModelMapper mapper;

    public Customer createCustomer(CustomerDto customerDto) {
        return mapper.map(customerDto, Customer.class);
    }

    public CustomerDto createCustomerDto(Customer customer) {
        return mapper.map(customer, CustomerDto.class);
    }
}
