package view;

import exception.InValidUserInfoException;
import service.CustomerService;
import validation.ValidationUtils;

import java.util.Scanner;

public class UserView {
    private CustomerService customerService = new CustomerService();
    private boolean isContinue;
    private Scanner scanner = new Scanner(System.in);

    public void addCustomer() {
        System.out.println("********* Customer information entry form ********");
        String info = getInformation();
        if (info != null) {
            customerService.save(customerService.createCustomer(info));
        }
    }

    public String getInformation() {
        isContinue = false;
        String info;
        do {
            System.out.println("Enter Information Like Sample: firstName,lastName,email,password,phoneNumber,credit");
            info = scanner.next();
            try {
                ValidationUtils.isValidInfo(info);
                isContinue = true;
                break;
            } catch (InValidUserInfoException e) {
                e.getMessage();
                isContinue = false;
            }
        } while (isContinue);
        return info;
    }

}
