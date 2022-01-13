package ir.maktab.data.dto;

import ir.maktab.data.model.enums.OrderStatus;
import ir.maktab.data.model.order.Address;
import ir.maktab.data.model.order.Suggestion;
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
    private Date doDate;
    private long receptionNumber;
    private double PricePaid;
    private SubServiceDto service;
    private OrderStatus status;
    private Address address;
    private ExpertDto expert;
    private CustomerDto customer;
    private List<Suggestion> suggestion;

}
