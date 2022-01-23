package ir.maktab.data.dto;

import ir.maktab.data.model.enums.UserRole;
import ir.maktab.data.model.enums.UserStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@EqualsAndHashCode
@NoArgsConstructor
@SuperBuilder
@Data
public class CustomerDto extends UserDto{

    private List<OrderDto> orderList = new ArrayList<>();

}
