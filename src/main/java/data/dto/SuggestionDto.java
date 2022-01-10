package data.dto;

import data.model.enums.SuggestionStatus;
import lombok.Data;

import java.util.Date;

@Data
public class SuggestionDto {
    private Date dateRegisterSuggest;
    private double ProposedPrice;
    private int durationOfWork;
    private int startTime;
    private SuggestionStatus status;
    private long receptionNumber;

}
