package ir.maktab.data.mapping;

import ir.maktab.data.dto.OrderDto;
import ir.maktab.data.model.order.Order;
import ir.maktab.utils.DateUtils;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.text.ParseException;
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

    @SneakyThrows
    public Order createOrder(OrderDto orderDto)  {
    Order order=Order.builder()
            .address(addressMap.createAddress(orderDto.getAddress()))
            .doDate(DateUtils.dateUtils(orderDto.getDoDate()))
            .service(subServiceMap.createSubService(orderDto.getService()))
            .jobDescription(orderDto.getJobDescription())
            .proposedPrice(orderDto.getProposedPrice())
            .pricePaid(orderDto.getPricePaid())
            .status(orderDto.getStatus())
            .receptionNumber(orderDto.getReceptionNumber())
            .customer(customerMap.createCustomer(orderDto.getCustomer()))
           .build();

      /*  if(orderDto.getReceptionNumber()!=0){
            order.setReceptionNumber(orderDto.getReceptionNumber());
        }*/
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
      OrderDto orderDto=OrderDto.builder()
                .address(addressMap.createAddressDto(order.getAddress()))
                .doDate(String.valueOf(order.getDoDate()))
                .service(subServiceMap.createSubServiceDto(order.getService()))
              .jobDescription(order.getJobDescription())
              .PricePaid(order.getPricePaid())
              .proposedPrice(order.getProposedPrice())
                .status(order.getStatus())
              .receptionNumber(order.getReceptionNumber())
                .customer(customerMap.createCustomerDto(order.getCustomer()))
                .build();

        /*if(order.getReceptionNumber()!=0){
            orderDto.setReceptionNumber(orderDto.getReceptionNumber());
        }*/
        if(order.getExpert()!=null){
            orderDto.setExpert(expertMap.createExpertDto(order.getExpert()));
        }
        if(order.getSuggestion().size()!=0){
            orderDto.setSuggestion(order.getSuggestion().stream().map(suggestionMap::createSuggestionDto)
                    .collect(Collectors.toList()));
        }

        return orderDto;

    }
}
