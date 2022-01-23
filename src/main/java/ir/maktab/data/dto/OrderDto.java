package ir.maktab.data.dto;

import ir.maktab.data.model.enums.OrderStatus;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Data
public class OrderDto {
    @Pattern(regexp ="[0-9]+$", message = "invalid proposedPrice")
    private double proposedPrice;
    private String jobDescription;
    @CreationTimestamp
    private Date registerDate;
    @Temporal(TemporalType.DATE)
    private Date doDate;
    private long receptionNumber;
    @Pattern(regexp ="[0-9]+$", message = "invalid proposedPrice")
    private double PricePaid;
    private SubServiceDto service;
    private OrderStatus status;
    private CustomerDto customer;
    private ExpertDto expert;
    private AddressDto address;
    private List<SuggestionDto> suggestion=new ArrayList<>();


}
