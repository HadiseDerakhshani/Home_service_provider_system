package ir.maktab.service.implemention;

import ir.maktab.data.dao.UserDao;
import ir.maktab.data.dto.UserDto;
import ir.maktab.data.mapping.UserMap;
import ir.maktab.data.model.enums.UserRole;
import ir.maktab.data.model.serviceSystem.SubService;
import ir.maktab.data.model.user.User;
import ir.maktab.exception.ObjectEntityNotFoundException;
import ir.maktab.service.UserService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Getter
@Service

public class UserServiceImpl implements UserService {

    private final UserDao userDao;

    private final UserMap userMap;

    @Autowired
    public UserServiceImpl(UserDao userDao, @Lazy UserMap userMap) {
        this.userDao = userDao;
        this.userMap = userMap;
    }

    @Override
    public UserDto findByEmail(String email) {
        if (userDao.findByEmail(email).isPresent())
            throw new ObjectEntityNotFoundException("user is not exit");
        else {
            User user = userDao.findByEmail(email).get();

            return userMap.createUserDto(user);
        }
    }

    @Override
    public boolean checkPassword(UserDto user, String pass) {
        if (userMap.createUser(user).getPassword().equals(pass)) {
            return true;
        }
        return false;
    }

    @Override
    public List<UserDto> filtering(String name, String family, String email, UserRole role, SubService service) {
        List<UserDto> listDto = new ArrayList<>();
        List<User> list = userDao.findAll(UserDao.filterByCriteria(name, family, email, role, service.getName()));
        if (list.size() != 0) {
            for (User user : list) {
                listDto.add(userMap.createUserDto(user));
            }
            return listDto;
        } else
            throw new ObjectEntityNotFoundException("-- user list not found --");
    }
}
