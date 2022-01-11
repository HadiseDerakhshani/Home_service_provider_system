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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import validation.ValidationInfo;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Service
public class ExpertService extends BaseService{

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


    public void changePassword(Expert expert, String newPass) {
        expert.setPassword(newPass);
        expertDao.save(expert);
        //   expertDao.updatePassword(newPass, expert.getEmail());
    }

    public void changePhoneNumber(Expert expert, String newPhoneNumber) {
        expert.setPhoneNumber(newPhoneNumber);
        expertDao.save(expert);
        // expertDao.updatePhoneNumber(newPhoneNumber, expert.getEmail());
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
            throw new IsNullObjectException("---not add Because this subService exit in expert service list---");
    }

    public void updateServiceList(List<SubService> list, Expert expert) {
        expert.setServiceList(list);
        expertDao.save(expert);
        //   expertDao.updateServiceList(list, email);
    }

    public List<ExpertDto> findAll(int first,int count) {
        Page<Expert> pageList = expertDao.findAll(PageRequest.of(first, count));
        List<Expert> list=pageList.toList() ;
        List<ExpertDto> listDto = new ArrayList<>();
        if (list != null) {
            for (Expert expert : list) {
                listDto.add(createExpertDto(expert));
            }
            return listDto;
        } else
            throw new IsNullObjectException(" --- list of expert is null ---");
    }
public long totalRecord(){
        return expertDao.count();

}
    public void deleteExpert(String email) {
        expertDao.delete(expertDao.findByEmail(email));
    }
    public void givenScoreAndComment(Expert expert){
      //////////
    }
}
