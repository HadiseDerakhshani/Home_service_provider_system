package ir.maktab.data.dto;

import ir.maktab.data.model.enums.SuggestionStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import java.util.Date;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class SuggestionDto {
    @CreationTimestamp
    private Date dateRegisterSuggest;

    private double proposedPrice;

    @Pattern(regexp = "[0-9]+$", message = "invalid durationOfWork")
    private int durationOfWork;

    @Min(value = 6, message = "min of start time is 6")
    @Max(value = 24, message = "max of start time is 24")
    private int startTime;

    private long receptionNumber;

    @Enumerated(EnumType.STRING)
    private SuggestionStatus status;

    private ExpertDto expert;

    private OrderDto order;


}
