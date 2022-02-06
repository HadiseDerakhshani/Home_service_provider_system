package ir.maktab.service;

import ir.maktab.data.dto.UserCategoryDto;
import ir.maktab.data.dto.UserDto;
import ir.maktab.data.entity.enums.UserRole;
import ir.maktab.data.entity.user.Customer;
import ir.maktab.data.entity.user.Expert;
import ir.maktab.data.entity.user.User;

import java.util.List;


public interface UserService {


    public UserRole findByEmail(String email, String pass);

    public boolean checkPassword(User user, String pass);

    public List<UserDto> filtering(UserCategoryDto categoryDto);

    public List<UserDto> findAll();

}
