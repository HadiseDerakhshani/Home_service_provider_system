package ir.maktab.data.dto;

import ir.maktab.data.model.enums.UserRole;
import ir.maktab.data.model.enums.UserStatus;
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
    private UserStatus userStatus;
    private Date dateRegister;
    private Date dateUpdate;
    private UserRole userRole;
    private double credit;
    private byte[] image;
    private int score;
    private List<SubServiceDto> serviceList = new ArrayList<>();
    private List<OrderDto> orderList = new ArrayList<>();
    private List<SuggestionDto> suggestList = new ArrayList<>();
    private List<CommentDto> commentList = new ArrayList<>();
}
