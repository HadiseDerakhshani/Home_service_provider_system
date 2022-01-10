package service;

import config.SpringConfig;
import data.dao.SuggestionDao;
import data.dto.SuggestionDto;
import data.model.enums.OrderStatus;
import data.model.enums.SuggestionStatus;
import data.model.enums.UserStatus;
import data.model.order.Order;
import data.model.order.Suggestion;
import data.model.user.Expert;
import exception.IsNullObjectException;
import org.modelmapper.ModelMapper;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SuggestionService {
    ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
    private SuggestionDao suggestionDao;
    private ModelMapper mapper = new ModelMapper();
    private OrderService orderService = context.getBean(OrderService.class);
    private ExpertService expertService = context.getBean(ExpertService.class);

    public SuggestionService(SuggestionDao suggestionDao) {
        this.suggestionDao = suggestionDao;
    }

    public SuggestionDto createSuggestDto(Suggestion suggestion) {

        return mapper.map(suggestion, SuggestionDto.class);
    }


    public Suggestion createSuggest(double price, int timeSpan, int time, Expert expert) {

        Suggestion suggestion = Suggestion.builder()
                .ProposedPrice(price)
                .durationOfWork(timeSpan)
                .startTime(time)
                .status(SuggestionStatus.NEW)
                .expert(expert)
                .build();
        if (!suggestionDao.exists(suggestion)) {
            suggestionDao.save(suggestion);
            return suggestion;
        } else
            throw new IsNullObjectException("---this suggestion exited---");
    }

    public void updateStatus(int index, List<SuggestionDto> list) {
        int count = 0;
        SuggestionDto suggestionDto = list.get(index - 1);
        list.remove(index - 1);
        for (SuggestionDto dto : list) {
            Suggestion suggest = suggestionDao.findByReceptionNumber(dto.getReceptionNumber());

            if (count == index - 1) {
                suggest.setStatus(SuggestionStatus.CONFIRMED);
                suggestionDao.save(suggest);
                Order order = suggest.getOrder();
                order.setStatus(OrderStatus.WAITING_FOR_THE_SPECIALIST_TO_COME_TO_YOUR_PLACE);
                orderService.save(order);
                Expert expert = suggest.getExpert();
                expert.setUserStatus(UserStatus.CONFIRMED);
                expertService.save(expert);
            } else
                suggest.setStatus(SuggestionStatus.REJECT);
            count++;
        }

    }
}
