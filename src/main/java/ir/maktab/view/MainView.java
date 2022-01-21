/*
package ir.maktab.view;

import ir.maktab.exception.InValidUserInfoException;
import ir.maktab.validation.ValidationInfo;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Getter
@Component
public class MainView {
    private final Scanner scanner = new Scanner(System.in);
    private final CustomerView customerView;
    private final ExpertView expertView;
    private final UserView userView;
    private final ManagerView managerView;
    private boolean isContinue;
    private String input;

    @Autowired
    public MainView(@Lazy CustomerView customerView, @Lazy ExpertView expertView, @Lazy UserView userView, @Lazy ManagerView managerView) {
        this.customerView = customerView;
        this.expertView = expertView;
        this.userView = userView;
        this.managerView = managerView;
    }

    public void mainMenu() {
        isContinue = false;
        do {
            System.out.println("select panel for show \n1.Manager \n2.User \n3.new customer");
            input = scanner.next();
            try {
                ValidationInfo.isValidSelected(input);

                switch (input) {
                    case "1" -> userView.loginUser();
                    case "2" -> managerView.loginManager();
                    case "3" -> customerView.addCustomer();
                }
                isContinue = true;
                break;
            } catch (InValidUserInfoException e) {
                System.out.println(e.getMessage());
            }
        } while (isContinue);
    }
}
*/
