package ir.maktab.data.dto;

import ir.maktab.data.model.enums.OrderStatus;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Data
public class OrderDto {

    private double proposedPrice;
    private String jobDescription;
    @CreationTimestamp
    private Date registerDate;
    @Temporal(TemporalType.DATE)
    private Date doDate;
    private long receptionNumber;
    private double PricePaid;
    private SubServiceDto service;
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
    private CustomerDto customer;
    private ExpertDto expert;
    private AddressDto address;
    private List<SuggestionDto> suggestion = new ArrayList<>();


}
