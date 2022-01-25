package ir.maktab.data.mapping;

import ir.maktab.data.dto.ExpertDto;
import ir.maktab.data.model.user.Expert;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

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
    /*
    *  private List<SubServiceDto> serviceList = new ArrayList<>();
    private List<OrderDto> orderList = new ArrayList<>();
    private List<SuggestionDto> suggestList = new ArrayList<>();
    private List<CommentDto> commentList = new ArrayList<>();*/

    public Expert createExpert(ExpertDto expertDto) {
        Expert expert=Expert.builder()
                .firstName(expertDto.getFirstName())
                .lastName(expertDto.getLastName())
                .email(expertDto.getEmail())
                .password(expertDto.getPassword())
                .phoneNumber(expertDto.getPhoneNumber())
                .credit(expertDto.getCredit())
                .build();
       if(expertDto.getServiceList().size()!=0){
           expert.setServiceList(expertDto.getServiceList().stream().map(subServiceMap::createSubService)
                   .collect(Collectors.toList()));
       }
        if(expertDto.getOrderList().size()!=0){
            expert.setOrderList(expertDto.getOrderList().stream().map(orderMap::createOrder)
                    .collect(Collectors.toList()));
        }
        if(expertDto.getSuggestList().size()!=0){
            expert.setSuggestList(expertDto.getSuggestList().stream().map(suggestionMap::createSuggestion)
                    .collect(Collectors.toList()));
        }
        if(expertDto.getCommentList().size()!=0){
            expert.setCommentList(expertDto.getCommentList().stream().map(commentMap::createComment)
                    .collect(Collectors.toList()));
        }
        return expert;
    }

    public ExpertDto createExpertDto(Expert expert) {

        return mapper.map(expert, ExpertDto.class);
    }
}
