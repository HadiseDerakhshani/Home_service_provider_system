package service;

import dao.CustomerDao;
import dto.CustomerDto;
import model.enums.UserStatus;
import model.person.Customer;

import java.util.List;

public class CustomerService {
    private final CustomerDao customerDao = new CustomerDao();

    public void save(Customer customer) {
        customer.setUserStatus(UserStatus.WAITING_CONFIRM);
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
                .userStatus(UserStatus.NEW)
                .credit(Double.parseDouble(split[5]))
                .build();
        return customer;
    }

    public List<CustomerDto> filter(String filter) {
        String name = null, family = null, email = null;
        boolean check = false;
        String[] split = filter.split(",");
        if (!split[0].equals("null"))
            name = split[0];
        if (!split[1].equals("null"))
            family = split[1];
        if (!split[2].equals("null"))
            email = split[2];
        List<CustomerDto> listFilter = customerDao.filter(name, family, email);
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
        customerFind.setUserStatus(UserStatus.WAITING_CONFIRM);
        return customerFind;
    }

    public void update(String input, String value, String email) {
        String query = null;
        int filed = 0;
        if (input.equals("6")) {
            filed = 6;
            query = "update Customer  c set c.credit=:newValue where c.email=:email";
        } else
            switch (input) {
                case "1" -> query = "update Customer  c set c.firstName=:newValue where c.email=:email";
                case "2" -> query = "update Customer  c set c.lastName=:newValue where c.email=:email";
                case "3" -> query = "update Customer  c set c.email=:newValue where c.email=:email";
                case "4" -> query = "update Customer  c set c.password=:newValue where c.email=:email";
                case "5" -> query = "update Customer  c set c.phoneNumber=:newValue where c.email=:email";

            }
        customerDao.update(query, value, email, filed);
    }
}
