package ir.maktab.service.implemention;

import ir.maktab.data.dto.*;
import ir.maktab.data.mapping.OrderMap;
import ir.maktab.data.mapping.SubServiceMap;
import ir.maktab.data.entity.enums.OrderStatus;
import ir.maktab.data.entity.enums.UserStatus;
import ir.maktab.data.entity.order.Order;
import ir.maktab.data.entity.order.Suggestion;
import ir.maktab.data.entity.user.Customer;
import ir.maktab.data.entity.user.Expert;
import ir.maktab.data.repasitory.OrderRepository;
import ir.maktab.exception.ObjectEntityNotFoundException;
import ir.maktab.service.OrderService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Service

public class OrderServiceImpl implements OrderService {

    private final OrderMap orderMap;

    private final OrderRepository orderRepository;
    private final SubServiceMap subServiceMap;
    private final SubServiceServiceImpl subServiceServiceImpl;
private final SuggestionServiceImpl suggestionService;
    private final ExpertServiceImpl expertServiceImpl;

    private final CustomerServiceImpl customerServiceImpl;

    @Autowired
    public OrderServiceImpl(@Lazy OrderMap orderMap, OrderRepository orderRepository, @Lazy SubServiceServiceImpl subServiceServiceImpl,
                            @Lazy ExpertServiceImpl expertServiceImpl, @Lazy CustomerServiceImpl customerServiceImpl,
                            @Lazy SubServiceMap subServiceMap,@Lazy SuggestionServiceImpl suggestionService) {
        this.orderMap = orderMap;
        this.orderRepository = orderRepository;
        this.subServiceServiceImpl = subServiceServiceImpl;
        this.expertServiceImpl = expertServiceImpl;
        this.customerServiceImpl = customerServiceImpl;
        this.subServiceMap = subServiceMap;
        this.suggestionService=suggestionService;
    }


    @Override
    public List<OrderDto> findOrderToSuggest() {
        List<Order> list = orderRepository.findByStatusOrStatus(OrderStatus.WAITING_FOR_EXPERT_SUGGESTION,
                OrderStatus.WAITING_FOR_EXPERT_SELECTION);
        if (list != null) {
            return list.stream().map(orderMap::createOrderDto).collect(Collectors.toList());
        } else
            throw new ObjectEntityNotFoundException("---order is not for given Suggestion---");
    }

    @Override
    public Order giveReceptionNumber(Order order) {
        order.setReceptionNumber(1000 + order.getId());
        return order;
    }

    @Override
    public OrderDto find(long number) {
        return orderMap.createOrderDto(findByReceptionNumber(number));
    }

    @Override
    public Order findByReceptionNumber(long number) {
        return orderRepository.findByReceptionNumber(number).get();
    }

    @Override
    public void updateStatus(OrderDto order, OrderStatus status) {
        Order orderFound = findByReceptionNumber(order.getReceptionNumber());
        orderFound.setStatus(status);
        orderRepository.save(orderFound);
    }

    @Override
    public void updatePricePaid(Order order, double amount) {
        Order orderFound = findByReceptionNumber(order.getReceptionNumber());
        orderFound.setPricePaid(amount);
        orderRepository.save(orderFound);
    }

    @Override
    public void addSuggestionToOrder(OrderDto order, SuggestionDto suggest) {
        Order orderFound = findByReceptionNumber(order.getReceptionNumber());
        if(order.getStatus().equals(OrderStatus.WAITING_FOR_EXPERT_SUGGESTION))
            orderFound.setStatus(OrderStatus.WAITING_FOR_EXPERT_SELECTION);
        Suggestion suggestion = suggestionService.findByReceptionNumber(suggest.getReceptionNumber());
        orderFound.getSuggestion().add(suggestion);
        orderRepository.save(orderFound);
    }

    @Override
    public void addExpertToOrder(ExpertDto expert, OrderDto order) {

        Order orderFound = findByReceptionNumber(order.getReceptionNumber());
        orderFound.setExpert(expertServiceImpl.findByEmail(expert.getEmail()).get());
        orderRepository.save(orderFound);
    }

    @Override
    public void addCustomerToOrder(CustomerDto customer, OrderDto order) {

        Order orderFound = findByReceptionNumber(order.getReceptionNumber());
        orderFound.setCustomer(customerServiceImpl.findByEmail(customer.getEmail()).get());
        orderRepository.save(orderFound);
    }

    @Override
    public void addServiceToOrder(SubServiceDto subServiceDto, OrderDto order) {

        Order orderFound = findByReceptionNumber(order.getReceptionNumber());
        orderFound.setService(subServiceServiceImpl.find(subServiceDto.getName()));
        orderRepository.save(orderFound);
    }

    @Override
    public List<OrderDto> findAll() {
        List<Order> list = orderRepository.findAll();
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
        List<Order> list = orderRepository.findAll();
        List<OrderDto> listFind = findAll();
        if (list != null) {
            List<Order> orderList = list.stream().filter(o -> o.getCustomer().getEmail().equals(customer.getEmail()))
                    .filter(o -> o.getStatus().equals(OrderStatus.WAITING_FOR_EXPERT_SELECTION.name()))
                    .collect(Collectors.toList());
            if (orderList != null) {
                return listFind = orderList.stream().map(orderMap::createOrderDto).collect(Collectors.toList());
            } else
                throw new ObjectEntityNotFoundException(" --- Order is not exit for select expert ---");
        } else
            throw new ObjectEntityNotFoundException(" --- Order is not exit yet ---");
    }

    @Override
    public List<OrderDto> findOrderToPayment(Customer customer) {
        List<Order> list = orderRepository.findAll();
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
      /*  Order order = findByReceptionNumber(number);
        if (order != null) {
            if (chose == 6)
                updateStatus(order, OrderStatus.STARTED);
            else if (chose == 7) {
                updateStatus(order, OrderStatus.DONE);
                expertServiceImpl.updateStatus(UserStatus.WAITING_CONFIRM, expert);
            }
        } else
            throw new ObjectEntityNotFoundException(" order is not exit");*/
    }

    public OrderDto save(OrderDto orderDto) {
        Order save = orderRepository.save(orderMap.createOrder(orderDto));
        Order order = giveReceptionNumber(save);
        orderRepository.save(order);
        return orderMap.createOrderDto(order);
    }
}