package ir.maktab.service;

import ir.maktab.data.dao.SuggestionDao;
import ir.maktab.data.dto.SuggestionDto;
import ir.maktab.data.mapping.ExpertMap;
import ir.maktab.data.mapping.SuggestionMap;
import ir.maktab.data.model.enums.OrderStatus;
import ir.maktab.data.model.enums.SuggestionStatus;
import ir.maktab.data.model.enums.UserStatus;
import ir.maktab.data.model.order.Order;
import ir.maktab.data.model.order.Suggestion;
import ir.maktab.data.model.user.Expert;
import ir.maktab.exception.ObjectEntityNotFoundException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;

@Getter
@Service
@RequiredArgsConstructor
public class SuggestionServiceImpl implements SuggestionService {
    private final SuggestionMap suggestionMap;
    private final ExpertMap expertMap;
    private final SuggestionDao suggestionDao;
    @Lazy
    private final ExpertService expertService;
    @Lazy
    private final OrderService orderService;

    @Override
    public Suggestion save(Suggestion suggestion) {
        return suggestionDao.save(suggestion);
    }

    @Override
    public Suggestion findByReceptionNumber(long number) {
        return suggestionDao.findByReceptionNumber(number).get();
    }

    @Override
    public Suggestion createSuggest(double price, int timeSpan, int time, Expert expert) {

        Suggestion suggestion = Suggestion.builder()
                .ProposedPrice(price)
                .durationOfWork(timeSpan)
                .startTime(time)
                .status(SuggestionStatus.NEW)
                .expert(expert)
                .build();
        if (suggestionDao.findByReceptionNumber(suggestion.getReceptionNumber()).isEmpty()) {
            suggestion = save(suggestion);
            updateReceptionNumber(suggestion);
            return suggestion;
        } else
            throw new ObjectEntityNotFoundException("---this suggestion exited---");
    }

    @Override
    public void updateReceptionNumber(Suggestion suggestion) {
        suggestionDao.updateReceptionNumber(suggestion.getId(), (suggestion.getId() + 1000));
    }

    @Override
    public void updateStatus(int id, SuggestionStatus status) {
        suggestionDao.updateStatus(id, status);
    }

    @Override
    public void update(int index, List<SuggestionDto> list) {
        int count = 0;
        for (SuggestionDto dto : list) {
            Suggestion suggest = suggestionDao.findByReceptionNumber(dto.getReceptionNumber()).get();
            if (count == index - 1) {
                updateStatus(suggest.getId(), SuggestionStatus.CONFIRMED);
                Order order = suggest.getOrder();
                orderService.updateStatus(order, OrderStatus.WAITING_FOR_EXPERT_TO_COME);
                Expert expert = suggest.getExpert();
                expertService.updateStatus(UserStatus.CONFIRMED, expert);
                orderService.updateExpert(expert, order);
            } else
                updateStatus(suggest.getId(), SuggestionStatus.REJECT);
            count++;
        }

    }
}
