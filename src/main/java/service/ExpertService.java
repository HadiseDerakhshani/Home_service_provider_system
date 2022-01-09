package service;

import data.dao.ExpertDao;
import data.dto.ExpertDto;
import data.model.enums.UserRole;
import data.model.enums.UserStatus;
import data.model.user.Expert;
import exception.InValidUserInfoException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import validation.ValidationInfo;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

@Service
public class ExpertService {

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

    public Expert findByEmail(String email) {
        return expertDao.findByEmail(email);
    }

    public Expert findByEmailAndUserStatus(String email, UserStatus status) {
        return expertDao.findByEmailAndUserStatus(email, status);

    }

    public ExpertDto findExpert(Expert expert) {
        return createExpertDto(findByEmail(expert.getEmail()));
    }

  /*  public void update(String input, String value, String email) {
        String query = null;
        int filed = 0;

        if (input.equals("6")) {
            filed = 6;
            query = "update Expert  e set e.credit=:newValue where e.email=:email";
        } else if (input.equals("7")) {
            filed = 7;
            query = "update Expert  e set e.score=:newValue where e.email=:email";
        } else
            switch (input) {
                case "1" -> query = "update Expert e set e.firstName=:newValue where e.email=:email";
                case "2" -> query = "update Expert e  set e.lastName=:newValue where e.email=:email";
                case "3" -> query = "update Expert e  set e.email=:newValue where e.email=:email";
                case "4" -> query = "update Customer  c set c.password=:newValue where c.email=:email";
                case "5" -> query = "update Customer  c set c.phoneNumber=:newValue where c.email=:email";
            }
        expertDao.update(query, value, email, filed);
    }*/

   /* public void addSuggest(int numberOrder, List<OrderDto> list, String input, Expert expert) {
        //int id = list.get(numberOrder - 1).getId();
        Suggestion suggest = suggestService.createSuggest(input, expert);
        orderService.updateStatus(5, OrderStatus.WAITING_FOR_SPECIALIST_SELECTION);
        orderService.updateSuggestion(5, suggest);
    }*/

}
