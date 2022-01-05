package dto;

import lombok.Data;
import model.serviceSystem.MasterDuty;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class ExpertDto {
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private Date dateRegister;
    private byte[] image;
    private int score;
    private List<MasterDuty> serviceList = new ArrayList<>();
}
