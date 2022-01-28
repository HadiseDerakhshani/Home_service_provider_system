package ir.maktab.service.implemention;

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
import ir.maktab.service.SuggestionService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;

@Getter
@Service

public class SuggestionServiceImpl implements SuggestionService {

    private final SuggestionMap suggestionMap;

    private final ExpertMap expertMap;
    private final SuggestionDao suggestionDao;

    private final ExpertServiceImpl expertServiceImpl;

    private final OrderServiceImpl orderServiceImpl;

    @Autowired
    public SuggestionServiceImpl(@Lazy SuggestionMap suggestionMap, @Lazy ExpertMap expertMap, SuggestionDao suggestionDao,
                                 @Lazy ExpertServiceImpl expertServiceImpl, @Lazy OrderServiceImpl orderServiceImpl) {
        this.suggestionMap = suggestionMap;
        this.expertMap = expertMap;
        this.suggestionDao = suggestionDao;
        this.expertServiceImpl = expertServiceImpl;
        this.orderServiceImpl = orderServiceImpl;
    }

    @Override
    public Suggestion save(Suggestion suggestion) {
        return suggestionDao.save(suggestion);
    }

    @Override
    public Suggestion findByReceptionNumber(long number) {
        return suggestionDao.findByReceptionNumber(number).get();
    }

   /* @Override
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
    }*/

    @Override
    public void updateReceptionNumber(Suggestion suggestion) {
        suggestion.setReceptionNumber((suggestion.getId() + 1000));
        suggestionDao.save(suggestion);
    }

    @Override
    public void updateStatus(Suggestion suggestion, SuggestionStatus status) {
        suggestion.setStatus(status);
        suggestionDao.save(suggestion);
    }

    @Override
    public void update(int index, List<SuggestionDto> list) {
        int count = 0;
        for (SuggestionDto dto : list) {
            //  Suggestion suggest = suggestionDao.findByReceptionNumber(dto.getReceptionNumber()).get();
            Suggestion suggest = suggestionMap.createSuggestion(dto);

            if (count == index - 1) {
                updateStatus(suggest, SuggestionStatus.CONFIRMED);
                Order order = suggest.getOrder();
                orderServiceImpl.updateStatus(order, OrderStatus.WAITING_FOR_EXPERT_TO_COME);
                Expert expert = suggest.getExpert();
                expertServiceImpl.updateStatus(UserStatus.CONFIRMED, expert);
                //  orderServiceImpl.updateExpert(expert, order);
            } else
                updateStatus(suggest, SuggestionStatus.REJECT);
            count++;
        }

    }
}
