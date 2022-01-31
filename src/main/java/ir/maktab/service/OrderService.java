package ir.maktab.service;

import ir.maktab.data.dto.*;
import ir.maktab.data.entity.enums.OrderStatus;
import ir.maktab.data.entity.order.Order;
import ir.maktab.data.entity.user.Customer;
import ir.maktab.data.entity.user.Expert;

import java.util.List;


public interface OrderService {


    public OrderDto save(OrderDto orderDto);

    public List<OrderDto> findOrderToSuggest();

    public OrderDto find(long number);
    public List<OrderDto> findOrder(CustomerDto customer);
    public void addCustomerToOrder(CustomerDto customer, OrderDto order);

    public void addServiceToOrder(SubServiceDto subServiceDto, OrderDto order);

    public Order giveReceptionNumber(Order order);

    public Order findByReceptionNumber(long number);

    public void updateStatus(OrderDto order, OrderStatus status);

    public void updatePricePaid(Order order, double amount);

    public void addSuggestionToOrder(OrderDto order, SuggestionDto suggest);

    public void addExpertToOrder(ExpertDto expert, OrderDto order);

    public List<OrderDto> findAll();

    public List<OrderDto> findOrderToSelectExpert(Customer customer);


    public List<OrderDto> findOrderToPayment(Customer customer);

    public void startAndEndOrder(int number, int chose, Expert expert);
}