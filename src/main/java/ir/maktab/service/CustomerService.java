package ir.maktab.service;

import ir.maktab.data.dto.CustomerDto;
import ir.maktab.data.dto.OrderDto;
import ir.maktab.data.dto.SuggestionDto;
import ir.maktab.data.entity.enums.UserStatus;
import ir.maktab.data.entity.user.Customer;

import java.util.List;
import java.util.Optional;


public interface CustomerService {


    Customer save(CustomerDto customerDto);

    List<CustomerDto> findByUserStatus(UserStatus status);

    CustomerDto find(String email);

    void deleteCustomer(String email);

    void changePassword(CustomerDto customer, String newPass);

    void updateOrder(CustomerDto customerDto, OrderDto orderDto);

    void updateStatus(String email, UserStatus status);

    void changePhoneNumber(CustomerDto customer, String newPhoneNumber);

    Optional<Customer> findByEmail(String email);

    void increaseCredit(Customer customer, double amount);

    void decreaseCredit(CustomerDto customer, double amount);

    List<CustomerDto> findAll(int pageNumber, int pageSize);

    List<CustomerDto> findAll();

    List<SuggestionDto> selectSuggestion(OrderDto orderDto);

    void payment(CustomerDto customerDto, OrderDto orderDto, double amount);

}
