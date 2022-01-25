package ir.maktab.data.mapping;

import ir.maktab.data.dto.CustomerDto;
import ir.maktab.data.model.order.Order;
import ir.maktab.data.model.user.Customer;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
@Data
@Component
@RequiredArgsConstructor
public class CustomerMap {
    private ModelMapper mapper;

    public Customer createCustomer(CustomerDto customerDto) {

        Customer customer = Customer.builder()
                .firstName(customerDto.getFirstName())
                .lastName(customerDto.getLastName())
                .password(customerDto.getPassword())
                .phoneNumber(customerDto.getPhoneNumber())
                .credit(customerDto.getCredit())
                .email(customerDto.getEmail())
                .build();
        OrderMap orderMap = new OrderMap();
        if(customerDto.getOrderList().size()!=0){
        List<Order> collect = customerDto.getOrderList().stream().map(orderMap::createOrder).collect(Collectors.toList());
        customer.setOrderList(collect);
        }
        return customer;
    }

    public CustomerDto createCustomerDto(Customer customer) {
        return mapper.map(customer, CustomerDto.class);
    }
}
