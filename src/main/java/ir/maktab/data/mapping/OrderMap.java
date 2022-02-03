package ir.maktab.data.mapping;

import ir.maktab.data.dto.OrderDto;
import ir.maktab.data.entity.order.Order;
import lombok.Data;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Data
@Component

public class OrderMap {
    private final ExpertMap expertMap;
    private final SuggestionMap suggestionMap;
    private final AddressMap addressMap;
    private final SubServiceMap subServiceMap;
    private final CustomerMap customerMap;
    private ModelMapper mapper;

    @Autowired
    public OrderMap(ModelMapper mapper, @Lazy ExpertMap expertMap, @Lazy SuggestionMap suggestionMap,
                    @Lazy AddressMap addressMap, @Lazy SubServiceMap subServiceMap, @Lazy CustomerMap customerMap) {
        this.mapper = mapper;
        this.expertMap = expertMap;
        this.suggestionMap = suggestionMap;
        this.addressMap = addressMap;
        this.subServiceMap = subServiceMap;
        this.customerMap = customerMap;
    }

    // @SneakyThrows
    public Order createOrder(OrderDto orderDto) {
        Order order = Order.builder()
                //  .doDate(DateUtils.dateUtils(orderDto.getDoDate()))
                .doDate(orderDto.getDoDate())
                .jobDescription(orderDto.getJobDescription())
                .proposedPrice(orderDto.getProposedPrice())
                .pricePaid(orderDto.getPricePaid())
                .status(orderDto.getStatus())
                .receptionNumber(orderDto.getReceptionNumber())
                .registerDate(orderDto.getRegisterDate())
                .build();

      /*  if(orderDto.getReceptionNumber()!=0){
            order.setReceptionNumber(orderDto.getReceptionNumber());
        }*/
        if (orderDto.getCustomer() != null) {
            order.setCustomer(customerMap.createCustomer(orderDto.getCustomer()));
        }
        if (orderDto.getExpert() != null) {
            order.setExpert(expertMap.createExpert(orderDto.getExpert()));
        }
        if (orderDto.getAddress() != null) {
            order.setAddress(addressMap.createAddress(orderDto.getAddress()));
        }
        if (orderDto.getService() != null) {
            order.setService(subServiceMap.createSubService(orderDto.getService()));
        }
        if (orderDto.getSuggestion() != null) {
            order.setSuggestion(orderDto.getSuggestion().stream().map(suggestionMap::createSuggestion)
                    .collect(Collectors.toList()));
        }
        return order;
    }

    public OrderDto createOrderDto(Order order) {
        OrderDto orderDto = OrderDto.builder()
                .doDate(order.getDoDate())
                .jobDescription(order.getJobDescription())
                .PricePaid(order.getPricePaid())
                .proposedPrice(order.getProposedPrice())
                .status(order.getStatus())
                .receptionNumber(order.getReceptionNumber())
                .registerDate(order.getRegisterDate())
                .build();

        /*if(order.getReceptionNumber()!=0){
            orderDto.setReceptionNumber(orderDto.getReceptionNumber());
        }*/
        if (order.getExpert() != null) {
            orderDto.setExpert(expertMap.createExpertDto(order.getExpert()));
        }
        if (order.getAddress() != null) {
            orderDto.setAddress(addressMap.createAddressDto(order.getAddress()));
        }
        if (order.getCustomer() != null) {
            orderDto.setCustomer(customerMap.createCustomerDto(order.getCustomer()));
        }
        if (order.getService() != null) {
            orderDto.setService(subServiceMap.createSubServiceDto(order.getService()));
        }
        if (order.getSuggestion() != null) {
            orderDto.setSuggestion(order.getSuggestion().stream().map(suggestionMap::createSuggestionDto)
                    .collect(Collectors.toList()));
        }

        return orderDto;

    }
}
