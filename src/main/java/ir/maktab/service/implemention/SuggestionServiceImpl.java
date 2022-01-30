package ir.maktab.service.implemention;

import ir.maktab.data.dto.ExpertDto;
import ir.maktab.data.dto.OrderDto;
import ir.maktab.data.dto.SuggestionDto;
import ir.maktab.data.mapping.ExpertMap;
import ir.maktab.data.mapping.SuggestionMap;
import ir.maktab.data.entity.enums.OrderStatus;
import ir.maktab.data.entity.enums.SuggestionStatus;
import ir.maktab.data.entity.enums.UserStatus;
import ir.maktab.data.entity.order.Order;
import ir.maktab.data.entity.order.Suggestion;
import ir.maktab.data.entity.user.Expert;
import ir.maktab.data.repasitory.SuggestionRepository;
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
    private final SuggestionRepository suggestionRepository;

    private final ExpertServiceImpl expertServiceImpl;

    private final OrderServiceImpl orderServiceImpl;

    @Autowired
    public SuggestionServiceImpl(@Lazy SuggestionMap suggestionMap, @Lazy ExpertMap expertMap, SuggestionRepository suggestionRepository,
                                 @Lazy ExpertServiceImpl expertServiceImpl, @Lazy OrderServiceImpl orderServiceImpl) {
        this.suggestionMap = suggestionMap;
        this.expertMap = expertMap;
        this.suggestionRepository = suggestionRepository;
        this.expertServiceImpl = expertServiceImpl;
        this.orderServiceImpl = orderServiceImpl;
    }

    @Override
    public SuggestionDto save(SuggestionDto suggestion, OrderDto orderDto, ExpertDto expertDto) {
        Order order = orderServiceImpl.findByReceptionNumber(orderDto.getReceptionNumber());
        Expert expert = expertServiceImpl.findByEmail(expertDto.getEmail()).get();
        Suggestion suggest = suggestionMap.createSuggestion(suggestion);
        suggest.setStatus(SuggestionStatus.NEW);
        suggest.setOrder(order);
        suggest.setExpert(expert);
        giveReceptionNumber(suggest);
        return suggestionMap.createSuggestionDto(suggestionRepository.save(suggest));
    }

    @Override
    public Suggestion findByReceptionNumber(long number) {
        return suggestionRepository.findByReceptionNumber(number).get();
    }


    @Override
    public void giveReceptionNumber(Suggestion suggestion) {
        suggestion.setReceptionNumber((suggestion.getId() + 1000));
        suggestionRepository.save(suggestion);
    }

    @Override
    public void updateStatus(Suggestion suggestion, SuggestionStatus status) {
        suggestion.setStatus(status);
        suggestionRepository.save(suggestion);
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
              //  orderServiceImpl.updateStatus(order, OrderStatus.WAITING_FOR_EXPERT_TO_COME);//todo
                Expert expert = suggest.getExpert();
                expertServiceImpl.updateStatus(UserStatus.CONFIRMED, expert);
                //  orderServiceImpl.updateExpert(expert, order);
            } else
                updateStatus(suggest, SuggestionStatus.REJECT);
            count++;
        }

    }
}
