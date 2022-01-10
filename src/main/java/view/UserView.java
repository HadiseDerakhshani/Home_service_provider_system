package view;

import data.model.user.User;
import exception.InValidUserInfoException;
import exception.IsNullObjectException;
import validation.ValidationInfo;

public class UserView extends BaseView {

    public void loginUser() {
        isContinue = false;
        do {
            System.out.println("enter email :");
            input = scanner.next();
            try {
                ValidationInfo.isValidEmail(input);
                checkPasswordUser(userService.findByEmail(input));
                isContinue = true;
                break;
            } catch (InValidUserInfoException | IsNullObjectException e) {
                e.getMessage();
            }
        } while (isContinue);
    }

    public void checkPasswordUser(User user) {
        isContinue = false;
        do {
            System.out.println("enter password");
            input = scanner.next();
            try {
                ValidationInfo.isValidPassword(input);
                if (userService.checkPassword(user, input)) {
                    showMenu(user);//menu
                    isContinue = true;
                    break;
                } else
                    System.out.println(" password is not correct");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } while (isContinue);
    }

    public void showMenu(User user) {

        switch (user.getUserRole().name()) {
            case "EXPERT":
                expertView.menuExpert(user.getEmail());
                break;
            case "CUSTOMER":
                customerView.menuCustomer(user.getEmail());
                break;
        }
        System.out.println(user);
    }
}
/*

    public void filterCustomer() {
        isContinue = false;
        String info;
        do {
            System.out.println("enter filter case name,family,emil:if not wanted case enter 0");
            info = scanner.next();
            try {
                info = ValidationFilterCustomer.isValidInfo(info);
                isContinue = true;
                break;
            } catch (InValidUserInfoException e) {
                e.getMessage();
                isContinue = false;
            }
        } while (isContinue);
        List<CustomerDto> filter = customerService.filter(info);
        filter.forEach(System.out::println);
    }
*/