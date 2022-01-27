package ir.maktab.data.mapping;

import ir.maktab.data.dto.CustomerDto;
import ir.maktab.data.model.order.Order;
import ir.maktab.data.model.user.Customer;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.expression.ParseException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
@Data
@Component

public class CustomerMap {
    private final ModelMapper mapper;
    private final OrderMap orderMap;
@Autowired
    public CustomerMap(ModelMapper mapper,@Lazy OrderMap orderMap) {
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

        if(customerDto.getOrderList().size()!=0) {
        List<Order> collect = customerDto.getOrderList().stream().map(orderMap::createOrder).collect(Collectors.toList());
        customer.setOrderList(collect);
        }
        return customer;
    }

    public CustomerDto createCustomerDto(Customer customer) {
        return mapper.map(customer, CustomerDto.class);
    }
}
