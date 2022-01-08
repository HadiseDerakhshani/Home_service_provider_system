package service;

import data.dto.CustomerDto;
import data.enums.OrderStatus;
import data.order.Order;
import data.user.Expert;

import java.util.*;

public class ManagerService {
    private CustomerService customerService = new CustomerService();
    private ExpertService expertService = new ExpertService();
    private OrderService orderService = new OrderService();
    private SuggestionService suggestionService = new SuggestionService();
    private ServiceService serviceService = new ServiceService();
    private SubServiceService subServiceService = new SubServiceService();


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
        Map<String, Double> priceMap = new HashMap<>();
        List<Order> orderList = orderService.findByStatus(OrderStatus.WAITING_FOR_SPECIALIST_SELECTION);
        int count = 0;
        for (Order order : orderList) {
            Expert expert = order.getSuggestion().get(count).getExpert();
            double priceExpert = order.getSuggestion().get(count).getProposedPrice();
            double priceCustomer = order.getProposedPrice();
            double difficult = (priceExpert - priceCustomer) <= 0 ? 0 : (priceExpert - priceCustomer);
            priceMap.put(expert.getEmail(), difficult);
            count++;
        }

        Optional<Map.Entry<String, Double>> minDifferentPrice = priceMap.entrySet().stream().min(Map.Entry.<String, Double>comparingByValue());
        ///expert ba sugest price nazdik customer
    }
}
