package data.dto;

import data.model.order.Address;
import lombok.Data;

import java.util.Date;

@Data
public class OrderDto {

    private double ProposedPrice;
    private String jobDescription;
    private Date doDate;
    private Address address;


}
