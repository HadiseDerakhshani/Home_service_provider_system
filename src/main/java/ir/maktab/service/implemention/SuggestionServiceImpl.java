package ir.maktab.service.implemention;

import ir.maktab.data.dto.ExpertDto;
import ir.maktab.data.dto.OrderDto;
import ir.maktab.data.dto.SuggestionDto;
import ir.maktab.data.entity.enums.OrderStatus;
import ir.maktab.data.entity.enums.SuggestionStatus;
import ir.maktab.data.entity.order.Order;
import ir.maktab.data.entity.order.Suggestion;
import ir.maktab.data.entity.user.Expert;
import ir.maktab.data.mapping.ExpertMap;
import ir.maktab.data.mapping.SuggestionMap;
import ir.maktab.data.repasitory.SuggestionRepository;
import ir.maktab.exception.ObjectEntityNotFoundException;
import ir.maktab.service.SuggestionService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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
        Suggestion saveSuggest = suggestionRepository.save(suggest);
        return suggestionMap.createSuggestionDto(suggestionRepository.save(giveReceptionNumber(saveSuggest)));
    }

    @Override
    public Suggestion findByReceptionNumber(long number) {
        if (suggestionRepository.findByReceptionNumber(number).isPresent())
            return suggestionRepository.findByReceptionNumber(number).get();
        throw new ObjectEntityNotFoundException(" ---  suggestion is not exit ---");
    }


    @Override
    public Suggestion giveReceptionNumber(Suggestion suggestion) {
        suggestion.setReceptionNumber(1000 + suggestion.getId());
        return suggestion;
    }

    @Override
    public void updateStatus(Suggestion suggestion, SuggestionStatus status) {
        suggestion.setStatus(status);
        suggestionRepository.save(suggestion);
    }


    @Override
    public void update(long number) {
        Suggestion suggestion = findByReceptionNumber(number);
        Order order = suggestion.getOrder();
        List<Suggestion> list = order.getSuggestion();
        Expert expert = suggestion.getExpert();
        order.setExpert(expert);
        suggestion.setStatus(SuggestionStatus.CONFIRMED);
        order.setStatus(OrderStatus.WAITING_FOR_EXPERT_TO_COME);

        List<Suggestion> suggestionList = list.stream().filter(s -> s.getReceptionNumber() != number)
                .collect(Collectors.toList());
        suggestionList.stream().forEach(s -> s.setStatus(SuggestionStatus.REJECT));
        order.setSuggestion(suggestionList);
        order.getSuggestion().add(suggestion);
        orderServiceImpl.update(order);
        expertServiceImpl.update(expert);
        suggestionRepository.save(suggestion);

    }

    @Override
    public List<SuggestionDto> findByOrder(long number) {
        List<Suggestion> list = suggestionRepository.findAll();
        List<Suggestion> suggestionList = list.stream().filter(s -> s.getOrder().getReceptionNumber() == number)
                .collect(Collectors.toList());
        return suggestionList.stream().sorted().map(suggestionMap::createSuggestionDto)
                .sorted(Comparator.comparingInt(s -> s.getExpert().getScore()))
                .sorted(Comparator.comparingDouble(s -> s.getProposedPrice()))
                .collect(Collectors.toList());
    }

    @Override
    public List<SuggestionDto> findByExpert(ExpertDto expertDto) {
        List<Suggestion> list = suggestionRepository.findAll();
        if (list != null) {
            List<Suggestion> suggestionList = list.stream().filter(s -> s.getExpert().getEmail().equals(expertDto.getEmail()))
                    .collect(Collectors.toList());
            if (suggestionList != null)
                return suggestionList.stream().sorted().map(suggestionMap::createSuggestionDto)
                        .collect(Collectors.toList());
        }
        throw new ObjectEntityNotFoundException(" --- list of suggestion is null ---");
    }
}
