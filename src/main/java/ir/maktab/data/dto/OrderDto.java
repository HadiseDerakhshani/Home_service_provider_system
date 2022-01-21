package ir.maktab.data.dto;

import ir.maktab.data.model.enums.OrderStatus;
import lombok.*;

import java.util.Date;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Data
public class OrderDto {

    private double ProposedPrice;
    private String jobDescription;
    private Date registerDate;
    private Date doDate;
    private long receptionNumber;
    private double PricePaid;
    private SubServiceDto service;
    private OrderStatus status;
    private CustomerDto customer;
    private ExpertDto expert;
    private AddressDto address;
    private List<SuggestionDto> suggestion;


}
