package ir.maktab.data.mapping;

import ir.maktab.data.dto.SuggestionDto;
import ir.maktab.data.model.order.Suggestion;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SuggestionMap {
    private ModelMapper mapper;

    public Suggestion createSuggestion(SuggestionDto suggestionDto) {
        return mapper.map(suggestionDto, Suggestion.class);
    }

    public SuggestionDto createSuggestionDto(Suggestion suggestion) {
        return mapper.map(suggestion, SuggestionDto.class);
    }
}
