package ir.maktab.data.mapping;

import ir.maktab.data.dto.UserDto;
import ir.maktab.data.entity.user.Customer;
import ir.maktab.data.entity.user.Expert;
import ir.maktab.data.entity.user.User;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Data
@Component
@RequiredArgsConstructor
public class UserMap {
    private ModelMapper mapper;

    public User createUser(UserDto userDto) {
        User user=User.builder()
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .phoneNumber(userDto.getPhoneNumber())
                .userStatus(userDto.getUserStatus())
                .userRole(userDto.getUserRole())
                .credit(userDto.getCredit())
                .dateUpdate(userDto.getDateUpdate())
                .dateRegister(userDto.getDateRegister()).build();
        return user;
    }

    public User mapCustomerDtoToUser(Customer customer) {
        User user = User.builder()
                .firstName(customer.getFirstName())
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .password(customer.getPassword())
                .phoneNumber(customer.getPhoneNumber())
                .credit(customer.getCredit())
                .email(customer.getEmail())
                .build();
        return user;
    }

    public User mapExpertDtoToUser(Expert expert) {
        User user = User.builder()
                .firstName(expert.getFirstName())
                .firstName(expert.getFirstName())
                .lastName(expert.getLastName())
                .password(expert.getPassword())
                .phoneNumber(expert.getPhoneNumber())
                .credit(expert.getCredit())
                .email(expert.getEmail())
                .build();
        return user;
    }

    public UserDto createUserDto(User user) {
        UserDto userDto=UserDto.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .password(user.getPassword())
                .phoneNumber(user.getPhoneNumber())
                .userStatus(user.getUserStatus())
                .userRole(user.getUserRole())
                .credit(user.getCredit())
                .dateUpdate(user.getDateUpdate())
                .dateRegister(user.getDateRegister()).build();
        return userDto;
    }
}
