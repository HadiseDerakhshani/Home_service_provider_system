package service;

import data.dao.OrderDao;
import data.dto.OrderDto;
import data.model.enums.OrderStatus;
import data.model.order.Address;
import data.model.order.Order;
import data.model.order.Suggestion;
import data.model.user.Customer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class OrderService {
    OrderDao orderDao = new OrderDao();

    public Order createOrder(String info, Customer customer, Address address, Suggestion suggestion) throws ParseException {
        String[] split = info.split(",");
        Order order = Order.builder()
                .ProposedPrice(Double.parseDouble(split[0]))
                .jobDescription(split[1])
                .doDate(new SimpleDateFormat("yyyy/mm/dd").parse(split[2]))
                .status(OrderStatus.WAITING_FOR_EXPERT_SUGGESTION)
                .customer(customer)
                .address(address)
                .build();
        orderDao.save(order);
        return order;
    }

    public List<OrderDto> findToGetSuggest() {
        return orderDao.findToGetSuggest();
    }

    public void updateStatus(int id, OrderStatus status) {
        orderDao.updateStatus(id, status);
    }

    public void updateSuggestion(int id, Suggestion suggest) {
        Order order = orderDao.findById(id);
        order.getSuggestion().add(suggest);
        orderDao.updateSuggestion(id, order.getSuggestion());
    }

    public List<Order> findByStatus(OrderStatus status) {
        return orderDao.findByStatus(status);
    }
}
