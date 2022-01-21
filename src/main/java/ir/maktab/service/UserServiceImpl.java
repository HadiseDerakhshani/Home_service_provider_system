package ir.maktab.service;

import ir.maktab.data.dao.UserDao;
import ir.maktab.data.dto.UserDto;
import ir.maktab.data.mapping.UserMap;
import ir.maktab.data.model.enums.UserRole;
import ir.maktab.data.model.serviceSystem.SubService;
import ir.maktab.data.model.user.User;
import ir.maktab.exception.ObjectEntityNotFoundException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Getter
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private final UserMap userMap;

    @Override
    public UserDto findByEmail(String email) {
        User user = userDao.findByEmail(email).get();
        if (user == null)
            throw new ObjectEntityNotFoundException("user is not exit");
        return userMap.createUserDto(user);
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
