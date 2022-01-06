package dto;

import lombok.Data;
import model.enums.UserStatus;

import java.util.Date;

@Data
public class CustomerDto {

    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private UserStatus userStatus;
    private Date dateRegister;
    private Date dateUpdate;
    private double credit;


}
