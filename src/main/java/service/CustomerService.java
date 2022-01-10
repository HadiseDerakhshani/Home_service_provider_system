package service;

import config.SpringConfig;
import data.dao.CustomerDao;
import data.dto.CustomerDto;
import data.dto.OrderDto;
import data.dto.SuggestionDto;
import data.model.enums.SuggestionStatus;
import data.model.enums.UserRole;
import data.model.enums.UserStatus;
import data.model.order.Suggestion;
import data.model.user.Customer;
import exception.InValidUserInfoException;
import exception.IsNullObjectException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerService {
    ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
    private CustomerDao customerDao;
    private ModelMapper mapper = new ModelMapper();
    private SuggestionService suggestService = context.getBean(SuggestionService.class);

    @Autowired
    public CustomerService(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    public void save(Customer customer) {
        customer.setUserStatus(UserStatus.WAITING_CONFIRM);
        customerDao.save(customer);
    }

    public CustomerDto createCustomerDto(Customer customer) {
        return mapper.map(customer, CustomerDto.class);
    }

    public Customer createCustomer(String name, String family, String email, String pass, String phone, double credit) {
        if (findByEmail(email) == null) {
            Customer customer = Customer.builder()
                    .firstName(name)
                    .lastName(family)
                    .email(email)
                    .password(pass)
                    .phoneNumber(phone)
                    .userStatus(UserStatus.NEW)
                    .userRole(UserRole.CUSTOMER)
                    .credit(credit)
                    .build();
            return customer;
        } else
            throw new InValidUserInfoException("Customer is exit for this email");
    }

    public CustomerDto findCustomer(Customer customer) {
        return createCustomerDto(findByEmail(customer.getEmail()));
    }

    public List<CustomerDto> findByUserStatus(UserStatus status) {
        List<Customer> list = customerDao.findByUserStatus(status);
        List<CustomerDto> listDto = new ArrayList<>();
        if (list != null) {
            for (Customer customer : list) {
                listDto.add(createCustomerDto(customer));
            }
            return listDto;
        } else
            throw new IsNullObjectException(" --- customer is not confirm ---");
    }

    public void updateStatus(UserStatus status, Customer customer) {
        customer.setUserStatus(status);
        customerDao.save(customer);
        // customerDao.updateStatus(status, customer.getEmail());
    }

    public List<Customer> findByFirstNameOrLastNameOrEmail(String name, String family, String email) {
        List<Customer> listFilter = customerDao.findByFirstNameOrLastNameOrEmail(name, family, email);
        return listFilter;
    }

    public void deleteCustomer(String email) {
        customerDao.delete(customerDao.findByEmail(email));
    }

    public void changePassword(Customer customer, String newPass) {
        customer.setPassword(newPass);
        customerDao.save(customer);
        // customerDao.updatePassword(newPass, customer.getEmail());
    }

    public void changePhoneNumber(Customer customer, String newPhoneNumber) {
        customer.setPhoneNumber(newPhoneNumber);
        customerDao.save(customer);
        //customerDao.updatePhoneNumber(newPhoneNumber, customer.getEmail());
    }

    public Customer findByEmailAndUserStatus(String email, UserStatus status) {
        return customerDao.findByEmailAndUserStatus(email, status);
    }

    public Customer findByEmail(String email) {
        return customerDao.findByEmail(email);
    }

    public void IncreaseCredit(Customer customer, double amount) {
        double credit = findByEmail(customer.getEmail()).getCredit();
        customer.setCredit(credit + amount);
        customerDao.save(customer);
    }

    public List<CustomerDto> findAll() {
        List<Customer> list = customerDao.findAll();
        List<CustomerDto> listDto = new ArrayList<>();
        if (list != null) {
            for (Customer customer : list) {
                listDto.add(createCustomerDto(customer));
            }
            return listDto;
        } else
            throw new IsNullObjectException(" --- list of customer is null ---");
    }

    public List<SuggestionDto> selectSuggestion(OrderDto orderDto) {
        List<Suggestion> suggestion = orderDto.getSuggestion().stream()
                .filter(s -> s.getStatus().equals(SuggestionStatus.NEW)).collect(Collectors.toList());

        List<SuggestionDto> suggestDtoList = new ArrayList<>();
        if (suggestion != null) {
            for (Suggestion suggest : suggestion) {
                SuggestionDto suggestDto = suggestService.createSuggestDto(suggest);
                suggestDtoList.add(suggestDto);
            }
            return suggestDtoList;
        } else
            throw new IsNullObjectException("-- list suggest null --");
    }
}
