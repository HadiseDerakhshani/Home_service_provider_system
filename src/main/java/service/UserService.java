package service;

import data.dao.UserDao;
import data.model.user.User;
import exception.IsNullObjectException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private UserDao userDao;

    @Autowired
    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public User findByEmail(String email) {
        User user = userDao.findByEmail(email);
        if (user == null)
            throw new IsNullObjectException("user is not exit");
        return user;
    }

    public boolean checkPassword(User user, String pass) {
        if (user.getPassword().equals(pass)) {
            return true;
        }
        return false;
    }
}
