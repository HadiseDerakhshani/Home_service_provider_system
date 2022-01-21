package ir.maktab.service;

import ir.maktab.data.dao.OrderDao;
import ir.maktab.data.dto.OrderDto;
import ir.maktab.data.dto.SubServiceDto;
import ir.maktab.data.mapping.OrderMap;
import ir.maktab.data.model.enums.OrderStatus;
import ir.maktab.data.model.enums.UserStatus;
import ir.maktab.data.model.order.Address;
import ir.maktab.data.model.order.Order;
import ir.maktab.data.model.order.Suggestion;
import ir.maktab.data.model.serviceSystem.SubService;
import ir.maktab.data.model.user.Customer;
import ir.maktab.data.model.user.Expert;
import ir.maktab.exception.ObjectEntityNotFoundException;
import ir.maktab.utils.DateUtils;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderMap orderMap;
    private final OrderDao orderDao;
    @Lazy
    private final SubServiceService subServiceService;
    @Lazy
    private final ExpertService expertService;
    @Lazy
    private final CustomerService customerService;

    @Override
    public Order createOrder(double price, String description, String date, Customer customer,
                             Address address, SubServiceDto service) throws ParseException {

        SubService subService = subServiceService.findByName(service.getName());
        if (subService != null) {
            Order order = Order.builder()
                    .proposedPrice(price)
                    .jobDescription(description)
                    .doDate(DateUtils.dateUtils(date))
                    .status(OrderStatus.WAITING_FOR_EXPERT_SUGGESTION)
                    .customer(customer)
                    .address(address)
                    .service(subService)
                    .build();
            order = orderDao.save(order);
            updateReceptionNumber(order);
            ///////unic fild make
            return order;
        } else throw new ObjectEntityNotFoundException("--- service is null ---");
    }

    @Override
    public List<OrderDto> findSuggest() {
        List<Order> list = orderDao.findByStatusOrStatus(OrderStatus.WAITING_FOR_EXPERT_SUGGESTION,
                OrderStatus.WAITING_FOR_EXPERT_SELECTION);
        if (list != null) {
            ArrayList<OrderDto> listOrderDto = new ArrayList<>();
            ///stream
            for (Order order : list) {
                listOrderDto.add(orderMap.createOrderDto(order));
            }
            return listOrderDto;
        } else
            throw new ObjectEntityNotFoundException("---order is not for given Suggestion---");
    }

    @Override
    public void updateReceptionNumber(Order order) {
        ///unic fild make search
        orderDao.updateReceptionNumber(order.getId(), 1000 + order.getId());
    }

    @Override
    public Order findByReceptionNumber(long number) {
        return orderDao.findByReceptionNumber(number).get();
    }

    @Override
    public void updateStatus(Order order, OrderStatus status) {

        orderDao.updateStatus(order.getId(), status);
    }

    @Override
    public void updatePricePaid(Order order, double amount) {

        orderDao.updatePricePaid(order.getId(), amount);
    }

    @Override
    public void updateSuggestion(Order order, Suggestion suggest) {

        orderDao.updateSuggestion(order.getId(), order.getSuggestion());
    }

    @Override
    public void updateExpert(Expert expert, Order order) {

        orderDao.updateExpert(order.getId(), expert);
    }

    @Override
    public List<OrderDto> findAll() {
        List<Order> list = orderDao.findAll();
        List<OrderDto> listDto = new ArrayList<>();
        if (list != null) {
            for (Order order : list) {
                listDto.add(orderMap.createOrderDto(order));
            }
            return listDto;
        } else
            throw new ObjectEntityNotFoundException(" --- Order is not exit ---");
    }

    @Override
    public List<OrderDto> findOrderToSelectExpert(Customer customer) {
        List<Order> list = orderDao.findAll();
        List<OrderDto> listFind = findAll();
        if (list != null) {
            List<Order> orderList = list.stream().filter(o -> o.getCustomer().getEmail().equals(customer.getEmail()))
                    .filter(o -> o.getStatus().equals(OrderStatus.WAITING_FOR_EXPERT_SELECTION.name()))
                    .collect(Collectors.toList());
            if (orderList != null) {
                for (Order order : orderList)
                    listFind.add(orderMap.createOrderDto(order));
                return listFind;
            } else
                throw new ObjectEntityNotFoundException(" --- Order is not exit for select expert ---");
        } else
            throw new ObjectEntityNotFoundException(" --- Order is not exit yet ---");
    }

    @Override
    public List<OrderDto> findOrderToPayment(Customer customer) {
        List<Order> list = orderDao.findAll();
        List<OrderDto> listFind = findAll();
        if (list != null) {
            List<Order> orderList = list.stream().filter(o -> o.getCustomer().getEmail().equals(customer.getEmail()))
                    .filter(o -> o.getStatus().equals(OrderStatus.DONE.name()))
                    .collect(Collectors.toList());
            if (orderList != null) {
                for (Order order : orderList)
                    listFind.add(orderMap.createOrderDto(order));
                return listFind;
            } else
                throw new ObjectEntityNotFoundException(" --- Order is not exit for payment---");
        } else
            throw new ObjectEntityNotFoundException(" --- Order is not exit yet ---");
    }

    @Override
    public void startAndEndOrder(int number, int chose, Expert expert) {
        Order order = findByReceptionNumber(number);
        if (order != null) {
            if (chose == 6)
                updateStatus(order, OrderStatus.STARTED);
            else if (chose == 7) {
                updateStatus(order, OrderStatus.DONE);
                expertService.updateStatus(UserStatus.WAITING_CONFIRM, expert);
            }
        } else
            throw new ObjectEntityNotFoundException(" order is not exit");
    }
}