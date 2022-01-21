package ir.maktab.data.dto;

import ir.maktab.data.model.enums.SuggestionStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
