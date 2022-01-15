package ir.maktab.data.dto;

import ir.maktab.data.model.enums.SuggestionStatus;
import ir.maktab.data.model.order.Order;
import ir.maktab.data.model.user.Expert;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import java.util.Date;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class SuggestionDto {
    private Date dateRegisterSuggest;
    private double ProposedPrice;
    private int durationOfWork;
    private int startTime;
    private long receptionNumber;
    private SuggestionStatus status;
    private ExpertDto expert;
    private OrderDto order;



}
