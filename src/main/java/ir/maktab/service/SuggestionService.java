package ir.maktab.service;

import ir.maktab.data.dto.SuggestionDto;
import ir.maktab.data.model.enums.SuggestionStatus;
import ir.maktab.data.model.order.Suggestion;

import java.util.List;


public interface SuggestionService {


    public Suggestion save(Suggestion suggestion);

    public Suggestion findByReceptionNumber(long number);


    public void updateReceptionNumber(Suggestion suggestion);

    public void updateStatus(Suggestion suggestion, SuggestionStatus status);

    public void update(int index, List<SuggestionDto> list);

}
