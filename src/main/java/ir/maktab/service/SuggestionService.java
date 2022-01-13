package ir.maktab.service;

import ir.maktab.data.dao.SuggestionDao;
import ir.maktab.data.dto.SuggestionDto;
import ir.maktab.data.model.enums.OrderStatus;
import ir.maktab.data.model.enums.SuggestionStatus;
import ir.maktab.data.model.enums.UserStatus;
import ir.maktab.data.model.order.Order;
import ir.maktab.data.model.order.Suggestion;
import ir.maktab.data.model.user.Expert;
import ir.maktab.exception.IsNullObjectException;
import lombok.Getter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;

@Getter
@Service
public class SuggestionService {
    private final ModelMapper mapper = new ModelMapper();
    private final SuggestionDao suggestionDao;
    private final ExpertService expertService;
    private final OrderService orderService;

    @Autowired
    public SuggestionService(SuggestionDao suggestionDao, @Lazy ExpertService expertService, @Lazy OrderService orderService) {
        this.suggestionDao = suggestionDao;
        this.expertService = expertService;
        this.orderService = orderService;
    }

    public Suggestion save(Suggestion suggestion) {
        return suggestionDao.save(suggestion);
    }

    public SuggestionDto createSuggestDto(Suggestion suggestion) {
        SuggestionDto suggest = SuggestionDto.builder()
                .dateRegisterSuggest(suggestion.getDateRegisterSuggest())
                .durationOfWork(suggestion.getDurationOfWork())
                .expert(expertService.createExpertDto(suggestion.getExpert()))
                .ProposedPrice(suggestion.getProposedPrice())
                .receptionNumber(suggestion.getReceptionNumber())
                .startTime(suggestion.getStartTime())
                .status(suggestion.getStatus())
                .build();
        return suggest;
    }


    public Suggestion createSuggest(double price, int timeSpan, int time, Expert expert) {

        Suggestion suggestion = Suggestion.builder()
                .ProposedPrice(price)
                .durationOfWork(timeSpan)
                .startTime(time)
                .status(SuggestionStatus.NEW)
                .expert(expert)
                .build();
        if (suggestionDao.findByReceptionNumber(suggestion.getReceptionNumber()) == null) {
            suggestion = save(suggestion);
            updateReceptionNumber(suggestion.getId(), (suggestion.getId() + 1000));
            return suggestion;
        } else
            throw new IsNullObjectException("---this suggestion exited---");
    }

    public void updateReceptionNumber(int id, int number) {
        suggestionDao.updateReceptionNumber(id, number);
    }

    public void updateStatus(int id, SuggestionStatus status) {
        suggestionDao.updateStatus(id, status);
    }

    public void update(int index, List<SuggestionDto> list) {
        int count = 0;
        for (SuggestionDto dto : list) {
            Suggestion suggest = suggestionDao.findByReceptionNumber(dto.getReceptionNumber());
            if (count == index - 1) {
                updateStatus(suggest.getId(), SuggestionStatus.CONFIRMED);
                Order order = suggest.getOrder();
                orderService.updateStatus(order, OrderStatus.WAITING_FOR_THE_SPECIALIST_TO_COME_TO_YOUR_PLACE);
                Expert expert = suggest.getExpert();
                expertService.updateStatus(UserStatus.CONFIRMED, expert);
                orderService.updateExpert(expert, order);
            } else
                updateStatus(suggest.getId(), SuggestionStatus.REJECT);
            count++;
        }

    }
}
