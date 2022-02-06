package ir.maktab.data.dto;

import ir.maktab.data.entity.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserCategoryDto extends BaseDto{
    private String firstName;
    private String lastName;
    private String email;
    private String service;
    private Integer score;
    private UserRole userRole;
}
