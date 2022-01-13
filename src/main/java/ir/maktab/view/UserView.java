package ir.maktab.view;

import ir.maktab.data.model.user.User;
import ir.maktab.exception.InValidUserInfoException;
import ir.maktab.exception.IsNullObjectException;
import ir.maktab.service.UserService;
import ir.maktab.validation.ValidationInfo;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Getter
@Component
public class UserView {
    private final Scanner scanner = new Scanner(System.in);
    private final UserService userService;
    private final CustomerView customerView;
    private final ExpertView expertView;
    private boolean isContinue;
    private String input;

    @Autowired
    public UserView(UserService userService, @Lazy CustomerView customerView, @Lazy ExpertView expertView) {
        this.userService = userService;
        this.customerView = customerView;
        this.expertView = expertView;
    }


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
                System.out.println(e.getMessage());
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
                    showMenu(user);
                    isContinue = true;
                    break;
                } else
                    System.out.println(" password is not correct");
            } catch (InValidUserInfoException e) {
                System.out.println(e.getMessage());
            }
        } while (isContinue);
    }

    public void showMenu(User user) {
        switch (user.getUserRole().name()) {
            case "EXPERT" -> expertView.menuExpert(user.getEmail());
            case "CUSTOMER" -> customerView.menuCustomer(user.getEmail());
        }
    }
}
