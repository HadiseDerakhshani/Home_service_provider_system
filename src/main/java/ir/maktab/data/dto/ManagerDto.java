package ir.maktab.data.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;


@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data

public class ManagerDto {
    @Pattern(regexp = "^[a-zA-Z]+$", message = "userName is not alphabet")
    private String username;


    @Pattern(regexp ="^[A-Za-z0-9._%+@|!&*=/-]{8,}$", message = "invalid password")
    private String password;

}
