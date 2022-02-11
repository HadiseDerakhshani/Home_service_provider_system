package ir.maktab.service;

import ir.maktab.data.dto.*;
import ir.maktab.data.entity.enums.OrderStatus;
import ir.maktab.data.entity.order.Order;
import ir.maktab.data.entity.user.Customer;
import ir.maktab.data.entity.user.Expert;

import java.util.List;


public interface OrderService {


  OrderDto save(OrderDto orderDto);

  List<OrderDto> findOrderToSuggest();

  OrderDto find(long number);

  List<OrderDto> findOrderByCustomer(CustomerDto customer);

  void addCustomerToOrder(CustomerDto customer, OrderDto order);

  void addServiceToOrder(SubServiceDto subServiceDto, OrderDto order);

  Order giveReceptionNumber(Order order);

  Order findByReceptionNumber(long number);

  void updateStatus(OrderDto order, OrderStatus status);

  void updatePricePaid(OrderDto order, double amount);

  void addSuggestionToOrder(OrderDto order, SuggestionDto suggest);

  void addExpertToOrder(ExpertDto expert, OrderDto order);

  List<OrderDto> findAll();

  List<OrderDto> findOrderToSelectExpert(Customer customer);

  OrderDto update(Order order);

  List<OrderDto> findOrderByExpert(ExpertDto expertDto);

  OrderDto findOrderToPayment(CustomerDto customer);

  void startAndEndOrder(int number, int chose, Expert expert);

  Double calculatePrice(OrderDto orderDto);

  List<OrderDto> filtering(OrderFilterDto orderFilterDto);
}