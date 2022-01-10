package view;

import config.SpringConfig;
import exception.InValidUserInfoException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import service.*;
import validation.ValidationInfo;

import java.util.Scanner;

public class BaseView {
    protected boolean isContinue;
    protected String input;
    protected Scanner scanner = new Scanner(System.in);
    ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
    protected UserService userService = context.getBean(UserService.class);
    protected CustomerService customerService = context.getBean(CustomerService.class);
    protected SubServiceService subServiceService = context.getBean(SubServiceService.class);
    protected ServiceService serviceService = context.getBean(ServiceService.class);
    protected OrderService orderService = context.getBean(OrderService.class);
    protected AddressService addressService = context.getBean(AddressService.class);
    protected ManagerService managerService = context.getBean(ManagerService.class);
    protected ExpertService expertService = context.getBean(ExpertService.class);
    protected SuggestionService suggestionService = context.getBean(SuggestionService.class);
    protected ExpertView expertView = context.getBean(ExpertView.class);
    protected CustomerView customerView = context.getBean(CustomerView.class);
    protected UserView userView = context.getBean(UserView.class);
    protected ManagerView managerView = context.getBean(ManagerView.class);

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
