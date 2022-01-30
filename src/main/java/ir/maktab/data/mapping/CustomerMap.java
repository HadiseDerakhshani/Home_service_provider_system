package ir.maktab.data.mapping;

import ir.maktab.data.dto.CustomerDto;
import ir.maktab.data.entity.order.Order;
import ir.maktab.data.entity.user.Customer;
import lombok.Data;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Component

public class CustomerMap {
    private final ModelMapper mapper;
    private final OrderMap orderMap;

    @Autowired
    public CustomerMap(ModelMapper mapper, @Lazy OrderMap orderMap) {
        this.mapper = mapper;
        this.orderMap = orderMap;
    }

    public Customer createCustomer(CustomerDto customerDto) {

        Customer customer = Customer.builder()
                .firstName(customerDto.getFirstName())
                .lastName(customerDto.getLastName())
                .password(customerDto.getPassword())
                .phoneNumber(customerDto.getPhoneNumber())
                .credit(customerDto.getCredit())
                .email(customerDto.getEmail())
                .build();

        if (customerDto.getOrderList() != null) {
            List<Order> collect = customerDto.getOrderList().stream().map(orderMap::createOrder).collect(Collectors.toList());
            customer.setOrderList(collect);
        }
        return customer;
    }

    public CustomerDto createCustomerDto(Customer customer) {
        CustomerDto customerDto = CustomerDto.builder()
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .password(customer.getPassword())
                .phoneNumber(customer.getPhoneNumber())
                .credit(customer.getCredit())
                .email(customer.getEmail())
                .build();

        if (customer.getOrderList() != null) {
            customerDto.setOrderList(customer.getOrderList().stream().map(orderMap::createOrderDto)
                    .collect(Collectors.toList()));
        }
        return customerDto;
    }
}
