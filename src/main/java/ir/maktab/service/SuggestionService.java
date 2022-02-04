package ir.maktab.service;

import ir.maktab.data.dto.ExpertDto;
import ir.maktab.data.dto.OrderDto;
import ir.maktab.data.dto.SuggestionDto;
import ir.maktab.data.entity.enums.SuggestionStatus;
import ir.maktab.data.entity.order.Suggestion;

import java.util.List;


public interface SuggestionService {


    public SuggestionDto save(SuggestionDto suggestion, OrderDto orderDto, ExpertDto expertDto);

    public Suggestion findByReceptionNumber(long number);
    public List<SuggestionDto> findByExpert(ExpertDto expertDto);

    public Suggestion giveReceptionNumber(Suggestion suggestion);

    public void updateStatus(Suggestion suggestion, SuggestionStatus status);

public List<SuggestionDto> findByOrder(long number);
    public void update(long number);
}
