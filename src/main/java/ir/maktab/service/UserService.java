package ir.maktab.service;

import ir.maktab.data.dto.UserDto;
import ir.maktab.data.entity.enums.UserRole;
import ir.maktab.data.entity.serviceSystem.SubService;

import java.util.List;


public interface UserService {

    public UserDto findByEmail(String email);

    public boolean checkPassword(UserDto user, String pass);

    public List<UserDto> filtering(String name, String family, String email, UserRole role, SubService service);
}
