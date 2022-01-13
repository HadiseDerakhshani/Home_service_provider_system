package ir.maktab.service;

import ir.maktab.data.dao.ExpertDao;
import ir.maktab.data.dto.ExpertDto;
import ir.maktab.data.dto.SubServiceDto;
import ir.maktab.data.model.enums.OrderStatus;
import ir.maktab.data.model.enums.UserRole;
import ir.maktab.data.model.enums.UserStatus;
import ir.maktab.data.model.order.Order;
import ir.maktab.data.model.order.Suggestion;
import ir.maktab.data.model.serviceSystem.SubService;
import ir.maktab.data.model.user.Expert;
import ir.maktab.exception.InValidUserInfoException;
import ir.maktab.exception.IsNullObjectException;
import ir.maktab.validation.ValidationInfo;
import lombok.Getter;
import org.modelmapper.ModelMapper;
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

public class ExpertService {
    private final ModelMapper mapper = new ModelMapper();
    private final ExpertDao expertDao;
    private final SubServiceService subServiceService;
    private final OrderService orderService;

    @Autowired
    public ExpertService(ExpertDao expertDao, @Lazy SubServiceService subServiceService, @Lazy OrderService orderService) {
        this.expertDao = expertDao;
        this.subServiceService = subServiceService;
        this.orderService = orderService;
    }

    public Expert save(Expert expert) {
        if (findByEmail(expert.getEmail()) == null) {
            expert.setUserStatus(UserStatus.WAITING_CONFIRM);
            return expertDao.save(expert);
        } else
            throw new InValidUserInfoException("-- Customer is exit for this email --");

    }

    public ExpertDto createExpertDto(Expert expert) {

        return mapper.map(expert, ExpertDto.class);
    }

    public Expert createExpert(String name, String family, String email, String pass, String phone,
                               double credit, int score, String image) {

        if (email == null || email == "")
            throw new IsNullObjectException("-- email is empty --");
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


    public void changePassword(Expert expert, String newPass) {

        expertDao.updatePassword(expert.getEmail(), newPass);
    }

    public void changePhoneNumber(Expert expert, String newPhoneNumber) {

        expertDao.updatePhoneNumber(expert.getEmail(), newPhoneNumber);
    }

    public Expert findByEmail(String email) {
        return expertDao.findByEmail(email);
    }


    public ExpertDto findExpert(Expert expert) {
        return createExpertDto(findByEmail(expert.getEmail()));
    }


    public void addSuggest(int number, Suggestion suggestion, Expert expert) {
        Order order = orderService.findByReceptionNumber(number);
        if (order.getStatus().equals(OrderStatus.WAITING_FOR_EXPERT_SUGGESTION.name()))
            orderService.updateStatus(order, OrderStatus.WAITING_FOR_SPECIALIST_SELECTION);
        orderService.updateSuggestion(order, suggestion);
    }

    public List<SubService> addSubServiceExpert(Expert expert, List<SubServiceDto> subServiceList, int index) {
        index--;

        SubServiceDto subServiceDto = subServiceList.get(index);

        SubService subService = subServiceService.findByName(subServiceDto.getName());
        SubService findService = null;

        for (SubService service : expert.getServiceList()) {
            if (service.getName().equals(subService.getName()))
                findService = service;
        }
        if (findService == null) {
            expert.getServiceList().add(subService);
            return expert.getServiceList();
        } else
            throw new IsNullObjectException("---not add Because this subService exit in expert ir.maktab.service list---");
    }

    public void updateServiceList(List<SubService> list, Expert expert) {

        expertDao.updateServiceList(expert.getEmail(), list);
    }

    public void updateScore(int score, Expert expert) {

        expertDao.updateScore(expert.getEmail(), score);
    }

    public void updateStatus(UserStatus status, Expert expert) {

        expertDao.updateStatus(expert.getEmail(), status);
    }

    public void updateCredit(double amount, Expert expert) {
        double credit = findByEmail(expert.getEmail()).getCredit();
        expertDao.updateCredit(expert.getEmail(), credit + amount);
    }

    public List<ExpertDto> findAll(int first, int count) {
        Page<Expert> pageList = expertDao.findAll(PageRequest.of(first, count));
        List<Expert> list = pageList.toList();
        List<ExpertDto> listDto = new ArrayList<>();
        if (list != null) {
            for (Expert expert : list) {
                listDto.add(createExpertDto(expert));
            }
            return listDto;
        } else
            throw new IsNullObjectException(" --- list of expert is null ---");
    }

    public long totalRecord() {
        return expertDao.count();
    }

    public void deleteExpert(String email) {
        expertDao.delete(expertDao.findByEmail(email));
    }

}
