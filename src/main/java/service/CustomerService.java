package service;

import dao.CustomerDao;
import dto.CustomerDto;
import model.enums.UserStatus;
import model.person.Customer;
import validation.ValidationFilter;

import java.io.IOException;
import java.util.List;

public class CustomerService {
    private final CustomerDao customerDao = new CustomerDao();

    public void save(Customer customer) {
        customerDao.save(customer);
    }

    public Customer createCustomer(String info) {
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

    public List<CustomerDto> filter(String filter) throws IOException {
        String name = null, family = null, email = null;
        boolean check = false;
        String validInfo = ValidationFilter.isValidInfo(filter);
        String[] split = validInfo.split(",");
        if (!split[0].equals("null"))
            name = split[0];
        if (!split[1].equals("null"))
            family = split[1];
        if (!split[2].equals("null"))
            email = split[2];
        List<CustomerDto> listFilter = customerDao.filter(name, family, email);
        return listFilter;
    }
}
