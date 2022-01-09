package service;

import config.SpringConfig;
import data.dao.ExpertDao;
import data.dto.ExpertDto;
import data.dto.SubServiceDto;
import data.model.enums.OrderStatus;
import data.model.enums.UserRole;
import data.model.enums.UserStatus;
import data.model.order.Order;
import data.model.order.Suggestion;
import data.model.serviceSystem.SubService;
import data.model.user.Expert;
import exception.InValidUserInfoException;
import exception.IsNullObjectException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Service;
import validation.ValidationInfo;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
public class ExpertService {

    ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
    public SubServiceService subServiceService = context.getBean(SubServiceService.class);
    public OrderService orderService = context.getBean(OrderService.class);

    private ExpertDao expertDao;

    @Autowired
    public ExpertService(ExpertDao expertDao) {
        this.expertDao = expertDao;
    }

    public void save(Expert expert) {
        expert.setUserStatus(UserStatus.WAITING_CONFIRM);
        expertDao.save(expert);
    }

    public ExpertDto createExpertDto(Expert expert) {
        ModelMapper mapper = new ModelMapper();
        return mapper.map(expert, ExpertDto.class);
    }

    public Expert createExpert(String name, String family, String email, String pass, String phone,
                               double credit, int score, String image) {
        if (findByEmail(email) == null) {
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
        } else
            throw new InValidUserInfoException("Expert is exit for this email");
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

    public List<Expert> findByFirstNameOrLastNameOrEmailOrServiceList(String name, String family, String email, List<data.model.serviceSystem.Service> list) {
        return expertDao.findByFirstNameOrLastNameOrEmailOrServiceList(name, family, email, list);
    }

    public boolean checkPassword(Expert expert, String pass) {
        if (expert.getPassword().equals(pass))
            return true;
        return false;

    }

    public void changePassword(Expert expert, String newPass) {
        expertDao.updatePassword(newPass, expert.getEmail());
    }

    public void changePhoneNumber(Expert expert, String newPhoneNumber) {
        expertDao.updatePhoneNumber(newPhoneNumber, expert.getEmail());
    }

    public Expert findByEmail(String email) {
        return expertDao.findByEmail(email);
    }

    public Expert findByEmailAndUserStatus(String email, UserStatus status) {
        return expertDao.findByEmailAndUserStatus(email, status);

    }

    public ExpertDto findExpert(Expert expert) {
        return createExpertDto(findByEmail(expert.getEmail()));
    }


    public void addSuggest(Order order, Suggestion suggestion, Expert expert) {
        if (order.getStatus().equals(OrderStatus.WAITING_FOR_EXPERT_SUGGESTION.name()))
            orderService.updateStatus(order.getId(), OrderStatus.WAITING_FOR_SPECIALIST_SELECTION);

        orderService.updateSuggestion(order, suggestion);
    }

    public void addSubServiceExpert(Expert expert, List<SubServiceDto> subServiceList, int index) {
        index--;
        Map.Entry<String, String> searchSubService = null;
        SubServiceDto subServiceDto = subServiceList.get(index);
        SubService subService = subServiceService.findByName(subServiceDto.getSubServiceMap());
        String nameSubService = subService.getSubServiceMap().keySet().stream().findAny().orElse(null);
        for (SubService service : expert.getServiceList()) {
            searchSubService = service.getSubServiceMap().entrySet().stream().filter(s -> s.getKey().equals(nameSubService))
                    .findAny().orElse(null);
        }
        if (searchSubService == null) {
            expert.getServiceList().add(subService);
            expertDao.updateServiceList(expert.getServiceList(), expert.getEmail());
        } else
            throw new IsNullObjectException("---not add Because this subService exit in expert service list---");
    }

}

