package ir.maktab.data.mapping;

import ir.maktab.data.dto.OrderDto;
import ir.maktab.data.model.order.Order;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderMap {
    private ModelMapper mapper;

    public Order createOrder(OrderDto orderDto) {
        return mapper.map(orderDto, Order.class);
    }

    public OrderDto createOrderDto(Order order) {
        return mapper.map(order, OrderDto.class);
    }
}
