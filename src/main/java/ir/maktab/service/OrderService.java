package ir.maktab.service;

import ir.maktab.data.dto.OrderDto;
import ir.maktab.data.dto.SubServiceDto;
import ir.maktab.data.model.enums.OrderStatus;
import ir.maktab.data.model.order.Address;
import ir.maktab.data.model.order.Order;
import ir.maktab.data.model.order.Suggestion;
import ir.maktab.data.model.user.Customer;
import ir.maktab.data.model.user.Expert;

import java.text.ParseException;
import java.util.List;


public interface OrderService {


    public Order createOrder(double price, String description, String date, Customer customer,
                             Address address, SubServiceDto service) throws ParseException;

    public List<OrderDto> findSuggest();

    public void updateReceptionNumber(Order order);

    public Order findByReceptionNumber(long number);

    public void updateStatus(Order order, OrderStatus status);

    public void updatePricePaid(Order order, double amount);

    public void updateSuggestion(Order order, Suggestion suggest);

    public void updateExpert(Expert expert, Order order);

    public List<OrderDto> findAll();

    public List<OrderDto> findOrderToSelectExpert(Customer customer);


    public List<OrderDto> findOrderToPayment(Customer customer);

    public void startAndEndOrder(int number, int chose, Expert expert);
}