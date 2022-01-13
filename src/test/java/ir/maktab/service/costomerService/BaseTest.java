package ir.maktab.service.costomerService;

import ir.maktab.config.SpringConfig;
import ir.maktab.data.model.enums.UserRole;
import ir.maktab.data.model.enums.UserStatus;
import ir.maktab.data.model.user.Customer;
import ir.maktab.service.CustomerService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class BaseTest {
    ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
    CustomerService customerService;

    Customer create(String name, String family, String email, String pass, String phone, double credit) {
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

    }
}
