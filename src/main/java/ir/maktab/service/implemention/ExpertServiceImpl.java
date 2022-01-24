package ir.maktab.service.implemention;

import ir.maktab.data.dao.ExpertDao;
import ir.maktab.data.dto.ExpertDto;
import ir.maktab.data.dto.SubServiceDto;
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

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Getter
@Service

public class ExpertServiceImpl implements ExpertService {

    private final ExpertMap expertMap;
    private final ExpertDao expertDao;
    private final SubServiceMap subServiceMap;

    private final SuggestionMap suggestionMap;

    private final SubServiceServiceImpl subServiceServiceImpl;

    private final OrderServiceImpl orderServiceImpl;

    private final SuggestionServiceImpl suggestionServiceImpl;

    @Autowired
    public ExpertServiceImpl(@Lazy ExpertMap expertMap, ExpertDao expertDao, @Lazy SubServiceServiceImpl subServiceServiceImpl,
                             @Lazy OrderServiceImpl orderServiceImpl, @Lazy SuggestionMap suggestionMap,
                             @Lazy SuggestionServiceImpl suggestionServiceImpl, @Lazy SubServiceMap subServiceMap) {
        this.expertMap = expertMap;
        this.expertDao = expertDao;
        this.subServiceServiceImpl = subServiceServiceImpl;
        this.orderServiceImpl = orderServiceImpl;
        this.suggestionServiceImpl = suggestionServiceImpl;
        this.suggestionMap = suggestionMap;
        this.subServiceMap = subServiceMap;
    }

    @Override
    public Expert save(ExpertDto expertDto) {
        if (findByEmail(expertDto.getEmail()) == null) {
            Expert expert = expertMap.createExpert(expertDto);
            expert.setUserStatus(UserStatus.WAITING_CONFIRM);
            expert.setUserRole(UserRole.EXPERT);
            return expertDao.save(expert);
        } else
            throw new InValidUserInfoException("-- Customer is exit for this email --");

    }


    @Override
    public ExpertDto addPicture(ExpertDto expert, String path) {

        File file = new File(path);
        byte[] imageFile = new byte[(int) file.length()];

        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            fileInputStream.read(imageFile);
            fileInputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        expert.setImage(imageFile);
        return expert;
    }

    @Override
    public void changePassword(ExpertDto expertDto, String newPass) {
        Expert expert = findByEmail(expertDto.getEmail());
        expert.setPassword(newPass);
        expertDao.save(expert);
    }

    @Override
    public void changePhoneNumber(ExpertDto expertDto, String newPhoneNumber) {
        Expert expert = findByEmail(expertDto.getEmail());
        expert.setPhoneNumber(newPhoneNumber);
        expertDao.save(expert);
    }

    @Override
    public Expert findByEmail(String email) {
        if (expertDao.findByEmail(email).isPresent())
            return expertDao.findByEmail(email).get();
        else
            throw new ObjectEntityNotFoundException(" expert is not exit");
    }

    @Override
    public void addSuggest(int number, SuggestionDto suggestionDto, ExpertDto expertDto) {
        Order order = orderServiceImpl.findByReceptionNumber(number);
        if (order.getStatus().equals(OrderStatus.WAITING_FOR_EXPERT_SUGGESTION))
            orderServiceImpl.updateStatus(order, OrderStatus.WAITING_FOR_EXPERT_SELECTION);
        orderServiceImpl.updateSuggestion(order, suggestionMap.createSuggestion(suggestionDto));
    }

    @Override
    public List<SubService> addSubServiceExpert(ExpertDto expertDto, List<SubServiceDto> subServiceDtoList, int index) {
        index--;

        SubServiceDto subServiceDto = subServiceDtoList.get(index);

        SubService subService = subServiceMap.createSubService(subServiceServiceImpl.findByName(subServiceDto.getName()));
        SubServiceDto findService = null;
        findService = expertDto.getServiceList().stream().filter(s -> s.getName().equals(subService.getName()))
                .findAny().orElse(null);

        if (findService == null) {
            Expert expert = expertMap.createExpert(expertDto);
            expert.getServiceList().add(subService);
            return expert.getServiceList();
        } else
            throw new ObjectEntityNotFoundException("---not add Because this subService exit in expert ir.maktab.service list---");
    }

    @Override
    public void updateServiceList(List<SubService> list, ExpertDto expert) {
        Expert expertFound = findByEmail(expert.getEmail());
        expertFound.setServiceList(list);
        expertDao.save(expertFound);
    }

    @Override
    public void updateScore(int score, Expert expert) {
        Expert expertFound = findByEmail(expert.getEmail());
        expertFound.setScore(score);
        expertDao.save(expertFound);

    }

    @Override
    public void updateStatus(UserStatus status, Expert expert) {
        Expert expertFound = findByEmail(expert.getEmail());
        expertFound.setUserStatus(status);
        expertDao.save(expertFound);
    }

    @Override
    public void updateCredit(double amount, Expert expert) {
        Expert expertFound = findByEmail(expert.getEmail());
        double credit = expertFound.getCredit();
        expertFound.setCredit(credit + amount);
        expertDao.save(expertFound);
    }

    @Override
    public List<ExpertDto> findAll(int pageNumber, int pageSize) {
        Page<Expert> pageList = expertDao.findAll(PageRequest.of(pageNumber, pageSize));
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
        return expertDao.count();
    }

    @Override
    public void deleteExpert(String email) {
        expertDao.delete(expertDao.findByEmail(email).get());
    }

}
