package ir.maktab.data.mapping;

import ir.maktab.data.dto.UserDto;
import ir.maktab.data.model.user.User;
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

    public UserDto createUserDto(User user) {
        return mapper.map(user, UserDto.class);
    }
}
