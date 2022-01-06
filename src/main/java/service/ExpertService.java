package service;

import dao.ExpertDao;
import dto.ExpertDto;
import dto.OrderDto;
import model.Suggestion;
import model.enums.OrderStatus;
import model.enums.UserStatus;
import model.person.Expert;
import validation.ValidationInfoExpert;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

public class ExpertService {
    private ExpertDao expertDao = new ExpertDao();
    private SuggestionService suggestService = new SuggestionService();
    private OrderService orderService = new OrderService();

    public void save(Expert expert) {
        expert.setUserStatus(UserStatus.WAITING_CONFIRM);
        expertDao.save(expert);
    }

    public Expert createExpert(String info) {
        String[] split = info.split(",");
        Expert expert = Expert.builder()
                .firstName(split[0])
                .lastName(split[1])
                .email(split[2])
                .password(split[3])
                .phoneNumber(split[4])
                .userStatus(UserStatus.NEW)
                .credit(Double.parseDouble(split[5]))
                .score(Integer.parseInt(split[6]))
                .build();
        return expert;
    }

    public Expert addPicture(Expert expert, String imageFileAddress) {

        File file = new File(imageFileAddress);
        byte[] imageFile = new byte[(int) file.length()];
        System.out.println(imageFile.length);
        try {

            ValidationInfoExpert.isValidByte(imageFile.length);
            FileInputStream fileInputStream = new FileInputStream(file);
            fileInputStream.read(imageFile);
            fileInputStream.close();

        } catch (IOException e) {
            e.printStackTrace();

        }
        expert.setImage(imageFile);
        return expert;
    }

    public List<ExpertDto> filter(String filter) {
        String name = null, family = null, email = null, duty = null;
        boolean check = false;
        String[] split = filter.split(",");
        if (!split[0].equals("null"))
            name = split[0];
        if (!split[1].equals("null"))
            family = split[1];
        if (!split[2].equals("null"))
            email = split[2];
        if (!split[2].equals("null"))
            duty = split[3];
        List<ExpertDto> listFilter = expertDao.filter(name, family, email, duty);
        return listFilter;
    }

    public boolean checkPassword(Expert expert, String pass) {
        if (expert.getPassword().equals(pass))
            return true;
        return false;

    }

    public Expert checkEmail(String email) {
        Expert expertFind = expertDao.findByEmail(email);
        expertFind.setUserStatus(UserStatus.WAITING_CONFIRM);
        return expertFind;
    }

    public void update(String input, String value, String email) {
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
    }

    public void addSuggest(int numberOrder, List<OrderDto> list, String input, Expert expert) {
        int id = list.get(numberOrder - 1).getId();
        Suggestion suggest = suggestService.createSuggest(input, expert);
        orderService.updateStatus(id, OrderStatus.WAITING_FOR_SPECIALIST_SELECTION);
        orderService.updateSuggestion(id, suggest);
    }

    public ExpertDto showExpert(String email) {
        return expertDao.showByEmail(email);
    }

}
