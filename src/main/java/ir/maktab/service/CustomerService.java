package ir.maktab.service;

import ir.maktab.data.dto.CustomerDto;
import ir.maktab.data.dto.OrderDto;
import ir.maktab.data.dto.SuggestionDto;
import ir.maktab.data.entity.enums.UserStatus;
import ir.maktab.data.entity.user.Customer;

import java.util.List;
import java.util.Optional;


public interface CustomerService {


    public Customer save(CustomerDto customerDto);

    public List<CustomerDto> findByUserStatus(UserStatus status);

    public CustomerDto find(String email);

    public void deleteCustomer(String email);

    public void changePassword(CustomerDto customer, String newPass);

    public void updateOrder(CustomerDto customerDto, OrderDto orderDto);

    public void updateStatus(String email, UserStatus status);

    public void changePhoneNumber(CustomerDto customer, String newPhoneNumber);

    public Optional<Customer> findByEmail(String email);

    public void increaseCredit(Customer customer, double amount);

    public void decreaseCredit(CustomerDto customer, double amount);

    public List<CustomerDto> findAll(int pageNumber, int pageSize);

    public List<SuggestionDto> selectSuggestion(OrderDto orderDto);

    public void payment(CustomerDto customerDto, OrderDto orderDto, double amount, int score, String commentText);

    public long totalRecord();
}
