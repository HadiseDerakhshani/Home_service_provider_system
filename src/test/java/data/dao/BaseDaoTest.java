package data.dao;

import config.SpringConfig;
import data.model.enums.UserRole;
import data.model.enums.UserStatus;
import data.model.user.Customer;
import lombok.Data;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@Data
public class BaseDaoTest {
    ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);

    public Customer createCustomer(String name, String family, String email, String pass, String phone, double credit, UserStatus status) {
        return Customer.builder()
                .lastName(name)
                .lastName(family)
                .password(pass)
                .phoneNumber(phone)
                .email(email)
                .userRole(UserRole.CUSTOMER)
                .userStatus(status)
                .credit(credit)
                .userStatus(status).build();

    }

}
