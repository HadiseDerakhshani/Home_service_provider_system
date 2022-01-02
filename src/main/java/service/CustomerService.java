package service;

import dao.CustomerDao;
import model.enums.UserStatus;
import model.person.Customer;

public class CustomerService {
    private final CustomerDao customerDao = new CustomerDao();

    public void save(Customer customer) {
        customerDao.save(customer);
    }

    public Customer createCustomer(String info) {//service or view
        String[] split = info.split(",");
        Customer customer = Customer.builder()
                .firstName(split[0])
                .lastName(split[1])
                .email(split[2])
                .password(split[3])
                .phoneNumber(split[4])
                .userStatus(UserStatus.WAITING_CONFIRM)
                .credit(Double.parseDouble(split[5]))
                .build();
        return customer;
    }

}
