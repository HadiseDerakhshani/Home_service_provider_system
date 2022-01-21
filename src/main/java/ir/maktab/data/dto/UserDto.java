package ir.maktab.data.dto;

import ir.maktab.data.model.enums.UserRole;
import ir.maktab.data.model.enums.UserStatus;
import lombok.Data;

import java.util.Date;

@Data
public class UserDto {
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private UserStatus userStatus;
    private Date dateRegister;
    private Date dateUpdate;
    private UserRole userRole;
    private double credit;

}
