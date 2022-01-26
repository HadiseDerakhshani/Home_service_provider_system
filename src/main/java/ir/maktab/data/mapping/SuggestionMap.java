package ir.maktab.data.mapping;

import ir.maktab.data.dto.SuggestionDto;
import ir.maktab.data.model.order.Suggestion;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
@Data
@Component

public class SuggestionMap {
    private final ModelMapper mapper;
    private  final ExpertMap expertMap;
    private final OrderMap orderMap;
@Autowired
    public SuggestionMap(ModelMapper mapper, @Lazy ExpertMap expertMap,@Lazy OrderMap orderMap) {
        this.mapper = mapper;
        this.expertMap = expertMap;
        this.orderMap = orderMap;
    }

    public Suggestion createSuggestion(SuggestionDto suggestionDto) {
        Suggestion suggestion=Suggestion.builder()
                .durationOfWork(suggestionDto.getDurationOfWork())
                .startTime(suggestionDto.getStartTime())
                .status(suggestionDto.getStatus())
                .build();
        if(suggestionDto.getProposedPrice()!=0){
            suggestion.setProposedPrice(suggestionDto.getProposedPrice());
        }
        if(suggestionDto.getReceptionNumber()!=0){
            suggestion.setReceptionNumber(suggestionDto.getReceptionNumber());
        }
        if(suggestionDto.getExpert()!=null){
            suggestion.setExpert(expertMap.createExpert(suggestionDto.getExpert()));
        }
        if (suggestionDto.getOrder()!=null){
            suggestion.setOrder(orderMap.createOrder(suggestionDto.getOrder()));
        }

        return mapper.map(suggestionDto, Suggestion.class);
    }

    public SuggestionDto createSuggestionDto(Suggestion suggestion) {
        return mapper.map(suggestion, SuggestionDto.class);
    }
}
