package ir.maktab.data.mapping;

import ir.maktab.data.dto.OrderDto;
import ir.maktab.data.model.order.Order;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Data
@Component

public class OrderMap {
    private ModelMapper mapper;
    private final ExpertMap expertMap;
    private final SuggestionMap suggestionMap;
    private final AddressMap addressMap;
    private  final SubServiceMap subServiceMap;
    private final CustomerMap customerMap;
@Autowired
    public OrderMap(ModelMapper mapper, @Lazy ExpertMap expertMap,@Lazy SuggestionMap suggestionMap,
                    @Lazy AddressMap addressMap,@Lazy SubServiceMap subServiceMap,@Lazy CustomerMap customerMap) {
        this.mapper = mapper;
        this.expertMap = expertMap;
        this.suggestionMap = suggestionMap;
        this.addressMap=addressMap;
        this.subServiceMap=subServiceMap;
        this.customerMap=customerMap;
    }

    public Order createOrder(OrderDto orderDto) {
    Order order=Order.builder()
            .address(addressMap.createAddress(orderDto.getAddress()))
            .doDate(orderDto.getDoDate())
          .service(subServiceMap.createSubService(orderDto.getService()))
            .status(orderDto.getStatus())
            .customer(customerMap.createCustomer(orderDto.getCustomer()))
           .build();
    if(orderDto.getJobDescription()!=null){
        order.setJobDescription(orderDto.getJobDescription());
    }
        if(orderDto.getProposedPrice()!=0){
            order.setProposedPrice(orderDto.getProposedPrice());
        }
        if(orderDto.getPricePaid()!=0){
            order.setPricePaid(orderDto.getPricePaid());
        }

        if(orderDto.getReceptionNumber()!=0){
            order.setReceptionNumber(orderDto.getReceptionNumber());
        }
        if(orderDto.getExpert()!=null){
            order.setExpert(expertMap.createExpert(orderDto.getExpert()));
        }
        if(orderDto.getSuggestion().size()!=0){
            order.setSuggestion(orderDto.getSuggestion().stream().map(suggestionMap::createSuggestion)
                    .collect(Collectors.toList()));
        }
        return order;
    }

    public OrderDto createOrderDto(Order order) {
        return mapper.map(order, OrderDto.class);
    }
}
