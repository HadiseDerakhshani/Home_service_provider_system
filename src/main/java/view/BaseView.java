package view;

import config.SpringConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import service.*;

import java.util.Scanner;

public class BaseView {
    public boolean isContinue;
    public String info;
    public Scanner scanner = new Scanner(System.in);
    ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
    public UserService userService = context.getBean(UserService.class);
    public CustomerService customerService = context.getBean(CustomerService.class);
    public SubServiceService subServiceService = context.getBean(SubServiceService.class);
    public ServiceService serviceService = context.getBean(ServiceService.class);
    public OrderService orderService = context.getBean(OrderService.class);
    public ExpertService expertService = context.getBean(ExpertService.class);
    public SuggestionService suggestionService = context.getBean(SuggestionService.class);
    public ExpertView expertView = context.getBean(ExpertView.class);
    public CustomerView customerView = context.getBean(CustomerView.class);
}
