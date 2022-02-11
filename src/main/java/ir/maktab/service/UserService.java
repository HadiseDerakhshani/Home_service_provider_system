package ir.maktab.service;

import ir.maktab.data.dto.UserCategoryDto;
import ir.maktab.data.dto.UserDto;
import ir.maktab.data.entity.enums.UserRole;
import ir.maktab.data.entity.user.User;

import java.util.List;


public interface UserService {


    UserRole findByEmail(String email, String pass);

    boolean checkPassword(User user, String pass);

    List<UserDto> filtering(UserCategoryDto categoryDto);

    List<UserDto> findAll();

}
