package service;

import dao.OrderDao;
import dto.OrderDto;
import model.Address;
import model.Order;
import model.Suggestion;
import model.enums.OrderStatus;
import model.person.Customer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class OrderService {
    OrderDao orderDao = new OrderDao();

    public void save(Order order) {
        orderDao.save(order);
    }

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
        return order;
    }

    public List<OrderDto> findToGetSuggest() {
        return orderDao.findToGetSuggest();
    }
}
