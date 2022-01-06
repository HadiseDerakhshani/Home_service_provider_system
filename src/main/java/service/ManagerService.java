package service;

import dto.CustomerDto;
import model.Order;
import model.enums.OrderStatus;
import model.person.Expert;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ManagerService {
    private CustomerService customerService = new CustomerService();
    private ExpertService expertService = new ExpertService();
    private OrderService orderService = new OrderService();
    private SuggestionService suggestionService = new SuggestionService();
    private MasterDutyService masterDutyService = new MasterDutyService();
    private BranchDutyService branchDutyService = new BranchDutyService();


    public void customerConfirmation() {
        List<CustomerDto> byStatus = customerService.findByStatus();
        List<String> customerListEmail = new ArrayList<>();
        for (CustomerDto customer : byStatus) {
            if (customer.getDateRegister().compareTo(customer.getDateUpdate()) == -1)
                customerListEmail.add(customer.getEmail());
        }
        customerService.updateStatus(customerListEmail);
    }

    public void expertConfirmation() {
       // Map<String, Double> priceMap = new HashMap<>();
        List<Order> orderList = orderService.findByStatus(OrderStatus.WAITING_FOR_SPECIALIST_SELECTION);
        int count = 0;
        for (Order order : orderList) {
            Expert expert = order.getSuggestion().get(count).getExpert();
            double priceExpert = order.getSuggestion().get(count).getProposedPrice();
            double priceCustomer = order.getProposedPrice();
            double difficult = (priceExpert - priceCustomer) <= 0 ? 0 : (priceExpert - priceCustomer);

        }
    }


}
