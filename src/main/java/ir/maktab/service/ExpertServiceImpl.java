package ir.maktab.service;

import ir.maktab.data.dao.ExpertDao;
import ir.maktab.data.dto.ExpertDto;
import ir.maktab.data.dto.SubServiceDto;
import ir.maktab.data.dto.SuggestionDto;
import ir.maktab.data.mapping.ExpertMap;
import ir.maktab.data.model.enums.OrderStatus;
import ir.maktab.data.model.enums.UserRole;
import ir.maktab.data.model.enums.UserStatus;
import ir.maktab.data.model.order.Order;
import ir.maktab.data.model.serviceSystem.SubService;
import ir.maktab.data.model.user.Expert;
import ir.maktab.exception.InValidUserInfoException;
import ir.maktab.exception.ObjectEntityNotFoundException;
import ir.maktab.validation.ValidationInfo;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class ExpertServiceImpl implements ExpertService {
    private final ExpertMap expertMap;
    private final ExpertDao expertDao;
    @Lazy
    private final SubServiceService subServiceService;
    @Lazy
    private final OrderService orderService;
    @Lazy
    private final SuggestionService suggestionService;

    @Override
    public Expert save(Expert expert) {
        if (findByEmail(expert.getEmail()) == null) {
            expert.setUserStatus(UserStatus.WAITING_CONFIRM);
            return expertDao.save(expert);
        } else
            throw new InValidUserInfoException("-- Customer is exit for this email --");

    }

    @Override
    public Expert createExpert(String name, String family, String email, String pass, String phone,
                               double credit, int score, String image) {

        if (email == null || email == "")
            throw new ObjectEntityNotFoundException("-- email is empty --");
        else {
            Expert expert = Expert.builder()
                    .firstName(name)
                    .lastName(family)
                    .email(email)
                    .password(pass)
                    .phoneNumber(phone)
                    .userStatus(UserStatus.NEW)
                    .credit(credit)
                    .score(score)
                    .userRole(UserRole.EXPERT)
                    .build();
            return addPicture(expert, image);

        }
    }

    @Override
    public Expert addPicture(Expert expert, String imageFileAddress) {

        File file = new File(imageFileAddress);
        byte[] imageFile = new byte[(int) file.length()];
        System.out.println(imageFile.length);
        try {

            ValidationInfo.isValidByte(imageFile.length);
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
        expertDao.updatePassword(expertMap.createExpert(expertDto).getEmail(), newPass);
    }

    @Override
    public void changePhoneNumber(ExpertDto expertDto, String newPhoneNumber) {

        expertDao.updatePhoneNumber(expertMap.createExpert(expertDto).getEmail(), newPhoneNumber);
    }

    @Override
    public Expert findByEmail(String email) {
        return expertDao.findByEmail(email).get();
    }

    @Override
    public void addSuggest(int number, SuggestionDto suggestionDto, ExpertDto expertDto) {
        Order order = orderService.findByReceptionNumber(number);
        if (order.getStatus().equals(OrderStatus.WAITING_FOR_EXPERT_SUGGESTION))
            orderService.updateStatus(order, OrderStatus.WAITING_FOR_EXPERT_SELECTION);

        orderService.updateSuggestion(order, suggestionService.findByReceptionNumber(suggestionDto.getReceptionNumber()));
    }

    @Override
    public List<SubService> addSubServiceExpert(ExpertDto expertDto, List<SubServiceDto> subServiceDtoList, int index) {
        index--;

        SubServiceDto subServiceDto = subServiceDtoList.get(index);

        SubService subService = subServiceService.findByName(subServiceDto.getName());
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

        expertDao.updateServiceList(expert.getEmail(), list);
    }

    @Override
    public void updateScore(int score, Expert expert) {

        expertDao.updateScore(expert.getEmail(), score);
    }

    @Override
    public void updateStatus(UserStatus status, Expert expert) {

        expertDao.updateStatus(expert.getEmail(), status);
    }

    @Override
    public void updateCredit(double amount, Expert expert) {
        double credit = findByEmail(expert.getEmail()).getCredit();
        expertDao.updateCredit(expert.getEmail(), credit + amount);
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
