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
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {
    ModelMapper mapper = new ModelMapper();
    private OrderDao orderDao;

    public OrderService(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    public void save(Order order) {
        orderDao.save(order);
    }

    public Order createOrder(double price, String description, String date, Customer customer, Address address) throws ParseException {

        Order order = Order.builder()
                .ProposedPrice(price)
                .jobDescription(description)
                .doDate(new SimpleDateFormat("yyyy/mm/dd").parse(date))
                .status(OrderStatus.WAITING_FOR_EXPERT_SUGGESTION)
                .customer(customer)
                .expert(null)
                .address(address)
                .build();
        order.setReceptionNumber(order.getId() + 1000);
        save(order);
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

    public Order findByReceptionNumber(long number) {
        return orderDao.findByReceptionNumber(number);
    }

    public OrderDto creatOrderDto(Order order) {
        return mapper.map(order, OrderDto.class);
    }

    public void updateStatus(Order order, OrderStatus status) {
        order.setStatus(status);
        orderDao.save(order);
        //  orderDao.updateStatus(status, id);
    }

    public void updateSuggestion(Order order, Suggestion suggest) {
        order.getSuggestion().add(suggest);
        orderDao.save(order);
        //  orderDao.updateSuggestion(order.getSuggestion(), order.getId());
    }

    public List<OrderDto> findAll() {
        List<Order> list = orderDao.findAll();
        List<OrderDto> listDto = new ArrayList<>();
        if (list != null) {
            for (Order order : list) {
                listDto.add(creatOrderDto(order));
            }
            return listDto;
        } else
            throw new IsNullObjectException(" --- Order is not exit ---");
    }

    public List<OrderDto> findOrderToSelectExpert(Customer customer) {
        List<Order> list = orderDao.findAll();
        List<OrderDto> listFind = findAll();
        if (list != null) {
            List<Order> collect = list.stream().filter(o -> o.getCustomer().getEmail().equals(customer.getEmail()))
                    .filter(o -> o.getStatus().equals(OrderStatus.WAITING_FOR_SPECIALIST_SELECTION))
                    .collect(Collectors.toList());
            if (collect != null) {
                for (Order order : collect)
                    listFind.add(creatOrderDto(order));
                return listFind;
            } else
                throw new IsNullObjectException(" --- Order is not exit ---");

        } else
            throw new IsNullObjectException(" --- Order is not exit ---");
    }

}
