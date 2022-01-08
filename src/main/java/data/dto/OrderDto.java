package data.dto;

import data.model.order.Address;
import lombok.Data;

import java.util.Date;

@Data
public class OrderDto {
    private int id;//-----
    private double ProposedPrice;
    private String jobDescription;
    private Date doDate;
    private Address address;

    @Override
    public String toString() {
        return "OrderDto{" +
                ", ProposedPrice=" + ProposedPrice +
                ", jobDescription='" + jobDescription + '\'' +
                ", doDate=" + doDate +
                ", address=" + address +
                '}';
    }
}
