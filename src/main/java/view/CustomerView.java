package view;

import data.model.user.Customer;
import org.springframework.stereotype.Service;

@Service
public class CustomerView extends BaseView {
    public void menuCustomer(String email) {
        Customer customer = customerService.findByEmail(email);
        isContinue = false;
        System.out.println("************ Welcome Customer ************");

    }
}
