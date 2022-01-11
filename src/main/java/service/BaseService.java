package service;

import config.SpringConfig;
import data.dao.CustomerDao;
import org.modelmapper.ModelMapper;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class BaseService {
    ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);

    protected ModelMapper mapper = new ModelMapper();
    protected SuggestionService suggestService = context.getBean(SuggestionService.class);
    protected OrderService orderService=context.getBean(OrderService.class);
    protected ExpertService  expertService=context.getBean(ExpertService.class);
    public SubServiceService subServiceService = context.getBean(SubServiceService.class);
    public CustomerService customerService = context.getBean(CustomerService.class);
}
