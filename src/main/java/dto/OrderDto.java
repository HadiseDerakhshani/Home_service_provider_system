package dto;

import lombok.Data;
import model.Address;

import java.util.Date;

@Data
public class OrderDto {
    private double ProposedPrice;
    private String jobDescription;
    private Date doDate;
    private Address address;

}
