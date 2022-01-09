package service;

import data.dao.OrderDao;
import data.dto.OrderDto;
import data.model.enums.OrderStatus;
import data.model.order.Address;
import data.model.order.Order;
import data.model.order.Suggestion;
import data.model.user.Customer;
import exception.IsNullObjectException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class OrderService {
    private OrderDao orderDao;

    public OrderService(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    public Order createOrder(double price, String description, String date, Customer customer, Address address) throws ParseException {

        Order order = Order.builder()
                .ProposedPrice(price)
                .jobDescription(description)
                .doDate(new SimpleDateFormat("yyyy/mm/dd").parse(date))
                .status(OrderStatus.WAITING_FOR_EXPERT_SUGGESTION)
                .customer(customer)
                .address(address)
                .build();

        orderDao.save(order);
        return order;
    }

    public List<OrderDto> findToGetSuggest() {
        List<Order> list = orderDao.findByStatusOrStatus(OrderStatus.WAITING_FOR_EXPERT_SUGGESTION,
                OrderStatus.WAITING_FOR_SPECIALIST_SELECTION);
        if (list != null) {
            ArrayList<OrderDto> listOrderDto = new ArrayList<>();
            for (Order order : list) {
                listOrderDto.add(creatOrderDto(order));
            }
            return listOrderDto;
        } else
            throw new IsNullObjectException("---order is not for given Suggestion---");
    }

    public Order findByRegisterDate(Date date) {
        return orderDao.findByRegisterDate(date);
    }

    public OrderDto creatOrderDto(Order order) {
        ModelMapper mapper = new ModelMapper();
        return mapper.map(order, OrderDto.class);
    }

    public void updateStatus(int id, OrderStatus status) {
        orderDao.updateStatus(status, id);
    }

    public void updateSuggestion(Order order, Suggestion suggest) {
        order.getSuggestion().add(suggest);
        orderDao.updateSuggestion(order.getSuggestion(), order.getId());
    }
/*
    public List<Order> findByStatus(OrderStatus status) {
        return orderDao.findByStatus(status);
    }*/
}
