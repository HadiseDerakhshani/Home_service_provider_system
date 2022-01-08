package service;

import data.dao.CustomerDao;
import data.model.enums.UserStatus;
import data.model.user.Customer;
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
        Customer customer = Customer.builder()
                .firstName(name)
                .lastName(family)
                .email(email)
                .password(pass)
                .phoneNumber(phone)
                .userStatus(UserStatus.NEW)
                .credit(credit)
                .build();
        return customer;
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

    public Customer checkEmail(String email) {
        Customer customerFind = customerDao.findByEmail(email);
        if (customerFind.getUserStatus().equals(UserStatus.CONFIRMED.name()))
            return customerFind;
        else return null;
    }

}
