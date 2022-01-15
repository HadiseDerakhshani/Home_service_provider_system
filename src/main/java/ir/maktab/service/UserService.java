package ir.maktab.service;

import ir.maktab.data.dao.UserDao;
import ir.maktab.data.dto.UserDto;
import ir.maktab.data.model.enums.UserRole;
import ir.maktab.data.model.serviceSystem.SubService;
import ir.maktab.data.model.user.User;
import ir.maktab.exception.ObjectEntityNotFoundException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Getter
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserDao userDao;
    private final ModelMapper mapper = new ModelMapper();


    public User findByEmail(String email) {
        User user = userDao.findByEmail(email).get();
        if (user == null)
            throw new ObjectEntityNotFoundException("user is not exit");
        return user;
    }

    public boolean checkPassword(User user, String pass) {
        if (user.getPassword().equals(pass)) {
            return true;
        }
        return false;
    }

    public UserDto createUserDto(User user) {

        return mapper.map(user, UserDto.class);
    }

    public List<UserDto> filtering(String name, String family, String email, UserRole role, SubService service) {
        List<UserDto> listDto = new ArrayList<>();
        List<User> list = userDao.findAll(UserDao.filterByCriteria(name, family, email, role, service));
        if (list.size() != 0) {
            for (User user : list) {
                listDto.add(createUserDto(user));
            }
            return listDto;
        } else
            throw new ObjectEntityNotFoundException("-- user list not found --");
    }
}
