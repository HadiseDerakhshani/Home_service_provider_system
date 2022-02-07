package ir.maktab.data.dto;

import ir.maktab.data.entity.order.Order;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data

public class TransactionDto {

    private long receptionNumber;
    private double amount;
    @OneToOne
    private Order order;
    @CreationTimestamp
    private Date registerDate;


}
