package ir.maktab.service.implemention;

import ir.maktab.data.repasitory.ExpertRepository;
import ir.maktab.data.repasitory.SubServiceRepository;
import ir.maktab.data.dto.ExpertDto;
import ir.maktab.data.dto.SuggestionDto;
import ir.maktab.data.mapping.ExpertMap;
import ir.maktab.data.mapping.SubServiceMap;
import ir.maktab.data.mapping.SuggestionMap;
import ir.maktab.data.model.enums.OrderStatus;
import ir.maktab.data.model.enums.UserRole;
import ir.maktab.data.model.enums.UserStatus;
import ir.maktab.data.model.order.Order;
import ir.maktab.data.model.serviceSystem.SubService;
import ir.maktab.data.model.user.Expert;
import ir.maktab.exception.InValidUserInfoException;
import ir.maktab.exception.ObjectEntityNotFoundException;
import ir.maktab.service.ExpertService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
@Service

public class ExpertServiceImpl implements ExpertService {

    private final ExpertMap expertMap;
    private final ExpertRepository expertRepository;
    private final SubServiceMap subServiceMap;
    private final SubServiceRepository subServiceRepository;
    private final SuggestionMap suggestionMap;

    private final SubServiceServiceImpl subServiceServiceImpl;

    private final OrderServiceImpl orderServiceImpl;

    private final SuggestionServiceImpl suggestionServiceImpl;

    @Autowired
    public ExpertServiceImpl(@Lazy ExpertMap expertMap, ExpertRepository expertRepository, @Lazy SubServiceServiceImpl subServiceServiceImpl,
                             @Lazy OrderServiceImpl orderServiceImpl, @Lazy SuggestionMap suggestionMap,
                             @Lazy SuggestionServiceImpl suggestionServiceImpl, @Lazy SubServiceMap subServiceMap,
                             @Lazy SubServiceRepository subServiceRepository) {
        this.expertMap = expertMap;
        this.expertRepository = expertRepository;
        this.subServiceServiceImpl = subServiceServiceImpl;
        this.orderServiceImpl = orderServiceImpl;
        this.suggestionServiceImpl = suggestionServiceImpl;
        this.suggestionMap = suggestionMap;
        this.subServiceMap = subServiceMap;
        this.subServiceRepository = subServiceRepository;
    }

    @Override
    public Expert save(ExpertDto expertDto) {
        if (!findByEmail(expertDto.getEmail()).isPresent()) {
            Expert expert = expertMap.createExpert(expertDto);
            expert.setUserStatus(UserStatus.WAITING_CONFIRM);
            expert.setUserRole(UserRole.EXPERT);
            return expertRepository.save(expert);
        } else
            throw new InValidUserInfoException("-- Expert is exit for this email --");
    }


    @Override
    public void updatePassword(ExpertDto expertDto, String newPass) {
        Expert expert = findByEmail(expertDto.getEmail()).get();
        expert.setPassword(newPass);
        expertRepository.save(expert);
    }

    @Override
    public void updatePhoneNumber(ExpertDto expertDto, String newPhoneNumber) {
        Expert expert = findByEmail(expertDto.getEmail()).get();
        expert.setPhoneNumber(newPhoneNumber);
        expertRepository.save(expert);
    }

    @Override
    public Optional<Expert> findByEmail(String email) {
        return expertRepository.findByEmail(email);
    }

    @Override
    public void addSuggest(int number, SuggestionDto suggestionDto, ExpertDto expertDto) {
        Order order = orderServiceImpl.findByReceptionNumber(number);
        if (order.getStatus().equals(OrderStatus.WAITING_FOR_EXPERT_SUGGESTION))
            orderServiceImpl.updateStatus(order, OrderStatus.WAITING_FOR_EXPERT_SELECTION);
        orderServiceImpl.addSuggestionToOrder(order, suggestionMap.createSuggestion(suggestionDto));
    }

    @Override
    public ExpertDto addSubServiceToExpert(ExpertDto expertDto, String name) {
        SubService subService = subServiceServiceImpl.find(name);
        Expert expert = expertMap.createExpert(expertDto);
        expert.getServiceList().add(subService);
        return expertMap.createExpertDto(expert);
    }

    @Override
    public void updateServiceList(List<SubService> list, ExpertDto expert) {
        Expert expertFound = findByEmail(expert.getEmail()).get();
        expertFound.setServiceList(list);
        expertRepository.save(expertFound);
    }

    @Override
    public void updateScore(int score, Expert expert) {
        Expert expertFound = findByEmail(expert.getEmail()).get();
        expertFound.setScore(score);
        expertRepository.save(expertFound);

    }

    @Override
    public void updateStatus(UserStatus status, Expert expert) {
        Expert expertFound = findByEmail(expert.getEmail()).get();
        expertFound.setUserStatus(status);
        expertRepository.save(expertFound);
    }

    @Override
    public void updateCredit(double amount, Expert expert) {
        Expert expertFound = findByEmail(expert.getEmail()).get();
        double credit = expertFound.getCredit();
        expertFound.setCredit(credit + amount);
        expertRepository.save(expertFound);
    }

    @Override
    public List<ExpertDto> findAll(int pageNumber, int pageSize) {
        Page<Expert> pageList = expertRepository.findAll(PageRequest.of(pageNumber, pageSize));
        List<Expert> list = pageList.toList();
        List<ExpertDto> listDto = new ArrayList<>();
        if (list != null) {
            for (Expert expert : list) {
                listDto.add(expertMap.createExpertDto(expert));
            }
            return listDto;
        } else
            throw new ObjectEntityNotFoundException(" --- list of expert is null ---");
    }

    @Override
    public long totalRecord() {
        return expertRepository.count();
    }

    @Override
    public void deleteExpert(String email) {
        expertRepository.delete(expertRepository.findByEmail(email).get());
    }

}
