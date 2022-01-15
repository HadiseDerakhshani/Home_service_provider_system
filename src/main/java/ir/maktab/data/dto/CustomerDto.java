package ir.maktab.data.dto;

import ir.maktab.data.model.enums.UserRole;
import ir.maktab.data.model.enums.UserStatus;
import ir.maktab.data.model.order.Comment;
import ir.maktab.data.model.order.Order;
import ir.maktab.data.model.order.Suggestion;
import ir.maktab.data.model.serviceSystem.SubService;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class CustomerDto {

    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private UserStatus userStatus;
    private Date dateRegister;
    private Date dateUpdate;
    private UserRole userRole;
    private double credit;
    private List<OrderDto> orderList = new ArrayList<>();

}
