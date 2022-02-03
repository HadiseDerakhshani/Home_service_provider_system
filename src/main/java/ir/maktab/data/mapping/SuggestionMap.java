package ir.maktab.data.mapping;

import ir.maktab.data.dto.SuggestionDto;
import ir.maktab.data.entity.order.Suggestion;
import lombok.Data;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Data
@Component

public class SuggestionMap {
    private final ModelMapper mapper;
    private final ExpertMap expertMap;
    private final OrderMap orderMap;

    @Autowired
    public SuggestionMap(ModelMapper mapper, @Lazy ExpertMap expertMap, @Lazy OrderMap orderMap) {
        this.mapper = mapper;
        this.expertMap = expertMap;
        this.orderMap = orderMap;
    }

    public Suggestion createSuggestion(SuggestionDto suggestionDto) {
        Suggestion suggestion = Suggestion.builder()
                .durationOfWork(suggestionDto.getDurationOfWork())
                .startTime(suggestionDto.getStartTime())
                .status(suggestionDto.getStatus())
                .ProposedPrice(suggestionDto.getProposedPrice())
                .receptionNumber(suggestionDto.getReceptionNumber())
                .build();

        if (suggestionDto.getExpert() != null) {
            suggestion.setExpert(expertMap.createExpert(suggestionDto.getExpert()));
        }
        if (suggestionDto.getOrder() != null) {
            suggestion.setOrder(orderMap.createOrder(suggestionDto.getOrder()));
        }

        return suggestion;
    }

    public SuggestionDto createSuggestionDto(Suggestion suggestion) {
        SuggestionDto suggest = SuggestionDto.builder()
                .durationOfWork(suggestion.getDurationOfWork())
                .startTime(suggestion.getStartTime())
                .status(suggestion.getStatus())
                .proposedPrice(suggestion.getProposedPrice())
                .receptionNumber(suggestion.getReceptionNumber())
                .build();

        if (suggestion.getExpert() != null) {
            suggest.setExpert(expertMap.createExpertDto(suggestion.getExpert()));
        }
        if (suggestion.getOrder() != null) {
            suggest.setOrder(orderMap.createOrderDto(suggestion.getOrder()));
        }

        return suggest;
    }
}
