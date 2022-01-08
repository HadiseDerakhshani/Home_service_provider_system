package data.dto;

import data.model.serviceSystem.Service;
import lombok.Data;

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
    private List<Service> serviceList = new ArrayList<>();
}
