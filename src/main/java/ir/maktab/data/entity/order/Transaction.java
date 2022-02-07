package ir.maktab.data.entity.order;

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
@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private long receptionNumber;
    private double amount;
    @OneToOne
    private Order order;
    @CreationTimestamp
    private Date registerDate;


}
