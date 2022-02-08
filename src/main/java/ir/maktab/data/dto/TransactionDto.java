package ir.maktab.data.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.OneToOne;
import java.util.Date;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data

public class TransactionDto {

    private long transactionNumber;
    private double amount;
    @OneToOne
    private OrderDto order;
    @CreationTimestamp
    private Date registerDate;


}
