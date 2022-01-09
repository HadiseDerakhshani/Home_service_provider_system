package service;

import data.dao.CustomerDao;
import data.dto.CustomerDto;
import data.model.enums.UserRole;
import data.model.enums.UserStatus;
import data.model.user.Customer;
import exception.InValidUserInfoException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {
    private CustomerDao customerDao;

    @Autowired
    public CustomerService(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    public void save(Customer customer) {
        customer.setUserStatus(UserStatus.WAITING_CONFIRM);
        customerDao.save(customer);
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

    public List<Customer> findByUserStatus(UserStatus status) {
        return customerDao.findByUserStatus(status);
    }

    public void updateStatus(UserStatus status, String email) {
        customerDao.updateStatus(status, email);
    }

    public List<Customer> findByFirstNameOrLastNameOrEmail(String name, String family, String email) {
        List<Customer> listFilter = customerDao.findByFirstNameOrLastNameOrEmail(name, family, email);
        return listFilter;
    }

    public boolean checkPassword(Customer customer, String pass) {
        if (customer.getPassword().equals(pass)) {
            return true;
        }
        return false;
    }

    public Customer findByEmailAndUserStatus(String email, UserStatus status) {
        return customerDao.findByEmailAndUserStatus(email, status);
    }

    public Customer findByEmail(String email) {
        return customerDao.findByEmail(email);
    }

    public CustomerDto createCustomerDto(Customer customer) {
        ModelMapper mapper = new ModelMapper();
        return mapper.map(customer, CustomerDto.class);
    }
}
