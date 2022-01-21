package ir.maktab.service;

import ir.maktab.data.dto.SuggestionDto;
import ir.maktab.data.model.enums.SuggestionStatus;
import ir.maktab.data.model.order.Suggestion;
import ir.maktab.data.model.user.Expert;

import java.util.List;


public interface SuggestionService {


    public Suggestion save(Suggestion suggestion);

    public Suggestion findByReceptionNumber(long number);

    public Suggestion createSuggest(double price, int timeSpan, int time, Expert expert);

    public void updateReceptionNumber(Suggestion suggestion);

    public void updateStatus(int id, SuggestionStatus status);

    public void update(int index, List<SuggestionDto> list);

}
