package ir.maktab.data.dto;

import ir.maktab.data.model.enums.UserRole;
import ir.maktab.data.model.enums.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.Date;

@Data
@SuperBuilder
@NoArgsConstructor
public class UserDto {
    @Pattern(regexp = "^[a-zA-Z]+$", message = "firstName is not alphabet")
    private String firstName;

    @Pattern(regexp = "^[a-zA-Z]+$", message = "lastName is not alphabet")
    private String lastName;

    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$", message = "invalid email")
    @NotBlank(message = "email is blank")
    private String email;

    @Pattern(regexp ="^[A-Za-z0-9._%+@|!&*=/-]{8,}$", message = "invalid password")
    private String password;

    @Pattern(regexp ="^(\\+98|0)?9\\d{9}$", message = "invalid phoneNumber")
    private String phoneNumber;

    private UserStatus userStatus;
    @CreationTimestamp
    private Date dateRegister;
    @UpdateTimestamp
    private Date dateUpdate;

    private UserRole userRole;


   // @Pattern(regexp ="[0-9]+$", message = "invalid credit")
    private double credit;

}
