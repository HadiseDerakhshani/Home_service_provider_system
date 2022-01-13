package ir.maktab.service;

import ir.maktab.data.dao.OrderDao;
import ir.maktab.data.dto.OrderDto;
import ir.maktab.data.dto.SubServiceDto;
import ir.maktab.data.model.enums.OrderStatus;
import ir.maktab.data.model.enums.UserStatus;
import ir.maktab.data.model.order.Address;
import ir.maktab.data.model.order.Order;
import ir.maktab.data.model.order.Suggestion;
import ir.maktab.data.model.serviceSystem.SubService;
import ir.maktab.data.model.user.Customer;
import ir.maktab.data.model.user.Expert;
import ir.maktab.exception.IsNullObjectException;
import lombok.Getter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Service
public class OrderService {
    private final ModelMapper mapper = new ModelMapper();

    private final OrderDao orderDao;
    SubServiceService subServiceService;
    ExpertService expertService;
    CustomerService customerService;

    @Autowired
    public OrderService(OrderDao orderDao, @Lazy SubServiceService subServiceService,
                        @Lazy ExpertService expertService, @Lazy CustomerService customerService) {
        this.orderDao = orderDao;
        this.subServiceService = subServiceService;
        this.expertService = expertService;
        this.customerService = customerService;
    }

    public Order save(Order order) {
        return orderDao.save(order);
    }

    public Order createOrder(double price, String description, String date, Customer customer,
                             Address address, SubServiceDto service) throws ParseException {

        SubService subService = subServiceService.findByName(service.getName());
        if (subService != null) {
            Order order = Order.builder()
                    .proposedPrice(price)
                    .jobDescription(description)
                    .doDate(new SimpleDateFormat("yyyy/mm/dd").parse(date))
                    .status(OrderStatus.WAITING_FOR_EXPERT_SUGGESTION)
                    .customer(customer)
                    .address(address)
                    .service(subService)
                    .build();
            order = save(order);
            updateReceptionNumber(order.getId(), order.getId() + 1000);
            return order;
        } else throw new IsNullObjectException("--- ir.maktab.service is null ---");
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

    public void updateReceptionNumber(int id, int number) {
        orderDao.updateReceptionNumber(id, number);
    }

    public Order findByReceptionNumber(long number) {
        return orderDao.findByReceptionNumber(number);
    }

    public OrderDto creatOrderDto(Order order) {

        OrderDto orderDto = OrderDto.builder()
                .service(subServiceService.createSubServiceDto(order.getService()))
                .address(order.getAddress())
                .expert(expertService.createExpertDto(order.getExpert()))
                .customer(customerService.createCustomerDto(order.getCustomer()))
                .doDate(order.getDoDate())
                .jobDescription(order.getJobDescription())
                .ProposedPrice(order.getProposedPrice())
                .PricePaid(order.getPricePaid())
                .status(order.getStatus())
                .suggestion(order.getSuggestion())
                .build();
        return orderDto;
    }

    public void updateStatus(Order order, OrderStatus status) {

        orderDao.updateStatus(order.getId(), status);
    }

    public void updatePricePaid(Order order, double amount) {

        orderDao.updatePricePaid(order.getId(), amount);
    }

    public void updateSuggestion(Order order, Suggestion suggest) {

        orderDao.updateSuggestion(order.getId(), order.getSuggestion());
    }

    public void updateExpert(Expert expert, Order order) {

        orderDao.updateExpert(order.getId(), expert);
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
                    .filter(o -> o.getStatus().equals(OrderStatus.WAITING_FOR_SPECIALIST_SELECTION.name()))
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


    public List<OrderDto> findOrderToPayment(Customer customer) {
        List<Order> list = orderDao.findAll();
        List<OrderDto> listFind = findAll();
        if (list != null) {
            List<Order> collect = list.stream().filter(o -> o.getCustomer().getEmail().equals(customer.getEmail()))
                    .filter(o -> o.getStatus().equals(OrderStatus.DONE.name()))
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
            throw new IsNullObjectException(" order is not exit");
    }
}