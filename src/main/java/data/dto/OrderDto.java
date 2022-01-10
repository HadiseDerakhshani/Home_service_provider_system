package data.dto;

import data.model.enums.OrderStatus;
import data.model.order.Address;
import data.model.order.Suggestion;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.List;

@EqualsAndHashCode
@Data
public class OrderDto {

    private double ProposedPrice;
    private String jobDescription;
    private Date doDate;
    private long receptionNumber;
    private OrderStatus status;
    private Address address;
    private List<Suggestion> suggestion;

}
