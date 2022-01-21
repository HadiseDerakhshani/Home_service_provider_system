package ir.maktab.service;

import ir.maktab.data.dto.CustomerDto;
import ir.maktab.data.dto.OrderDto;
import ir.maktab.data.dto.SuggestionDto;
import ir.maktab.data.model.enums.UserStatus;
import ir.maktab.data.model.user.Customer;

import java.util.List;


public interface CustomerService {


    public Customer save(Customer customer);


    public Customer createCustomer(String name, String family, String email, String pass, String phone, double credit);


    public List<CustomerDto> findByUserStatus(UserStatus status);


    public void deleteCustomer(String email);

    public void changePassword(CustomerDto customer, String newPass);

    public void updateStatus(String email, UserStatus status);

    public void changePhoneNumber(CustomerDto customer, String newPhoneNumber);

    public Customer findByEmail(String email);

    public void increaseCredit(Customer customer, double amount);

    public void decreaseCredit(CustomerDto customer, double amount);

    public List<CustomerDto> findAll(int pageNumber, int pageSize);

    public List<SuggestionDto> selectSuggestion(OrderDto orderDto);

    public void payment(CustomerDto customerDto, OrderDto orderDto, double amount, int score, String commentText);

    public long totalRecord();
}
