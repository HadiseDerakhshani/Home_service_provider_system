package ir.maktab.data.mapping;

import ir.maktab.data.dto.ExpertDto;
import ir.maktab.data.model.user.Expert;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.stream.Collectors;

@Component
@Data
public class ExpertMap {
    private final ModelMapper mapper;
    private final SubServiceMap subServiceMap;
    private final SuggestionMap suggestionMap;
    private final OrderMap orderMap;
    private final CommentMap commentMap;
@Autowired
    public ExpertMap(ModelMapper mapper,@Lazy SubServiceMap subServiceMap,
                     @Lazy SuggestionMap suggestionMap,@Lazy OrderMap orderMap,@Lazy CommentMap commentMap) {
        this.mapper = mapper;
        this.subServiceMap = subServiceMap;
        this.suggestionMap = suggestionMap;
        this.orderMap = orderMap;
        this.commentMap = commentMap;
    }

    public Expert createExpert(ExpertDto expertDto) {
        Expert expert=Expert.builder()
                .firstName(expertDto.getFirstName())
                .lastName(expertDto.getLastName())
                .email(expertDto.getEmail())
                .password(expertDto.getPassword())
                .phoneNumber(expertDto.getPhoneNumber())
                .credit(expertDto.getCredit())
                .build();
       if(expertDto.getServiceList()!=null){
           expert.setServiceList(expertDto.getServiceList().stream().map(subServiceMap::createSubService)
                   .collect(Collectors.toList()));
       }
        if(expertDto.getOrderList()!=null){
            expert.setOrderList(expertDto.getOrderList().stream().map(orderMap::createOrder)
                    .collect(Collectors.toList()));
        }
        if(expertDto.getSuggestList()!=null){
            expert.setSuggestList(expertDto.getSuggestList().stream().map(suggestionMap::createSuggestion)
                    .collect(Collectors.toList()));
        }
        if(expertDto.getCommentList()!=null){
            expert.setCommentList(expertDto.getCommentList().stream().map(commentMap::createComment)
                    .collect(Collectors.toList()));
        }
        return expert;
    }

    public ExpertDto createExpertDto(Expert expert) {
        ExpertDto expertDto=ExpertDto.builder()
                .firstName(expert.getFirstName())
                .lastName(expert.getLastName())
                .email(expert.getEmail())
                .password(expert.getPassword())
                .phoneNumber(expert.getPhoneNumber())
                .credit(expert.getCredit())
                .build();
        if(expert.getServiceList()!=null){
            expertDto.setServiceList(expert.getServiceList().stream().map(subServiceMap::createSubServiceDto)
                    .collect(Collectors.toList()));
        }
        if(expert.getOrderList()!=null){
            expertDto.setOrderList(expert.getOrderList().stream().map(orderMap::createOrderDto)
                    .collect(Collectors.toList()));
        }
        if(expert.getSuggestList()!=null){
            expertDto.setSuggestList(expert.getSuggestList().stream().map(suggestionMap::createSuggestionDto)
                    .collect(Collectors.toList()));
        }
        if(expertDto.getCommentList()!=null){
            expertDto.setCommentList(expert.getCommentList().stream().map(commentMap::createCommentDto)
                    .collect(Collectors.toList()));
        }
        return expertDto;
    }
}
