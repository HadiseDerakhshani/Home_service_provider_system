package service;

import dao.ExpertDao;
import dto.ExpertDto;
import model.enums.UserStatus;
import model.person.Expert;
import validation.ValidationInfoExpert;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

public class ExpertService {
    ExpertDao expertDao = new ExpertDao();

    public void save(Expert expert) {
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
                .userStatus(UserStatus.WAITING_CONFIRM)
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
}
