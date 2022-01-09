package view;

import config.SpringConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import service.*;

import java.util.Scanner;

public class BaseView {
    protected boolean isContinue;
    protected String info;
    protected Scanner scanner = new Scanner(System.in);
    ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
    protected UserService userService = context.getBean(UserService.class);
    protected CustomerService customerService = context.getBean(CustomerService.class);
    protected SubServiceService subServiceService = context.getBean(SubServiceService.class);
    protected ServiceService serviceService = context.getBean(ServiceService.class);
    protected OrderService orderService = context.getBean(OrderService.class);
    protected AddressService addressService = context.getBean(AddressService.class);
    protected ExpertService expertService = context.getBean(ExpertService.class);
    protected SuggestionService suggestionService = context.getBean(SuggestionService.class);
    protected ExpertView expertView = context.getBean(ExpertView.class);
    protected CustomerView customerView = context.getBean(CustomerView.class);
}
