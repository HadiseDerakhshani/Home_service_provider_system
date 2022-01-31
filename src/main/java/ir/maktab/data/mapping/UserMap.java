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

        return mapper.map(userDto, User.class);
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
        return mapper.map(user, UserDto.class);
    }
}
