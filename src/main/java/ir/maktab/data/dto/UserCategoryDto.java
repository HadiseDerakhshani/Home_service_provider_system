package ir.maktab.data.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
public class UserCategoryDto extends BaseDto {
    private String firstName;
    private String lastName;
    private String email;
    private String service;
    private Integer score;
    private String userRole;
}
