package model;

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
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private double ProposedPrice;
    private String jobDescription;
    @CreationTimestamp
    private Date registerDate;
    private Date doDate;
    @OneToOne(cascade = CascadeType.ALL)
    private Address address;
}
