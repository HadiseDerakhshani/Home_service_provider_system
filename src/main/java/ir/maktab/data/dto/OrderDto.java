package ir.maktab.data.dto;

import ir.maktab.data.model.enums.OrderStatus;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
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

    private double proposedPrice;
    private String jobDescription;

    private Date registerDate;

    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private Date doDate;

    private long receptionNumber;
    private double PricePaid;
    private SubServiceDto service;
    private OrderStatus status;
    private CustomerDto customer;
    private ExpertDto expert;
    private AddressDto address;
    private List<SuggestionDto> suggestion = new ArrayList<>();


}
