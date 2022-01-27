package ir.maktab.data.dto;

import ir.maktab.data.model.enums.OrderStatus;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

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
    @CreationTimestamp
    private Date registerDate;

  @Pattern(regexp = "^[1-4]\\d{3}\\/((0[1-6]\\/((3[0-1])|([1-2][0-9])|(0[1-9])))|((1[0-2]|(0[7-9]))\\/(30|31|"+
 " ([1-2][0-9])|(0[1-9]))))$\n")
    private String doDate;
    private long receptionNumber;
    private double PricePaid;
    private SubServiceDto service;
    private OrderStatus status;
    private CustomerDto customer;
    private ExpertDto expert;
    private AddressDto address;
    private List<SuggestionDto> suggestion = new ArrayList<>();


}
