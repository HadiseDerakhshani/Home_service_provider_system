package ir.maktab.service.implemention;

import ir.maktab.data.dto.UserCategoryDto;
import ir.maktab.data.dto.UserDto;
import ir.maktab.data.entity.enums.UserRole;
import ir.maktab.data.entity.serviceSystem.SubService;
import ir.maktab.data.entity.user.Customer;
import ir.maktab.data.entity.user.Expert;
import ir.maktab.data.entity.user.User;
import ir.maktab.data.mapping.UserMap;
import ir.maktab.data.repasitory.UserRepository;
import ir.maktab.data.repasitory.UserSpecifications;
import ir.maktab.exception.ObjectEntityNotFoundException;
import ir.maktab.service.UserService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserMap userMap;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, @Lazy UserMap userMap) {
        this.userRepository = userRepository;
        this.userMap = userMap;
    }


    @Override
    public UserRole findByEmail(String email, String pass) {
        if (!userRepository.findByEmail(email).isPresent())
            throw new ObjectEntityNotFoundException("user is not exit");
        else {
            User user = userRepository.findByEmail(email).get();
            if (checkPassword(user, pass)) ;
            return user.getUserRole();
        }
    }

    @Override
    public boolean checkPassword(User user, String pass) {
        if (user.getPassword().equals(pass))
            return true;

        return false;
    }

    @Override
    public List<UserDto> filtering(UserCategoryDto categoryDto) {
        Pageable pageable = PageRequest.of(categoryDto.getPageNumber(), categoryDto.getPageSize());
        Specification<User> specification = UserSpecifications.filterByCriteria(categoryDto);
        return userRepository
                .findAll(specification, pageable)
                .stream()
                .map(userMap::createUserDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserDto> findAll() {
        List<User> list = userRepository.findAll();
        return list.stream().map(userMap::createUserDto).collect(Collectors.toList());
    }




}  /*List<UserDto> listDto = new ArrayList<>();
    List<User> list = userRepository.findAll(UserRepository.filterByCriteria(name, family, email, role, service.getName()));
        if (list.size() != 0) {
                for (User user : list) {
                listDto.add(userMap.createUserDto(user));
                }
                return listDto;
                } else
                throw new ObjectEntityNotFoundException("-- user list not found --");
*/