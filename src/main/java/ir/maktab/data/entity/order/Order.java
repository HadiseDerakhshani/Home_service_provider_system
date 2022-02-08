package ir.maktab.data.entity.order;

import ir.maktab.data.entity.enums.OrderStatus;
import ir.maktab.data.entity.serviceSystem.SubService;
import ir.maktab.data.entity.user.Customer;
import ir.maktab.data.entity.user.Expert;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@EqualsAndHashCode
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private double proposedPrice;
    private String jobDescription;
    @CreationTimestamp
    @Temporal(TemporalType.DATE)
    private Date registerDate;
    private Date doDate;
    private long receptionNumber;
    private double pricePaid;
    @ManyToOne(cascade = CascadeType.ALL)
    private SubService service;
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
    @ManyToOne(cascade = CascadeType.ALL)
    private Customer customer;
    @ManyToOne(cascade = CascadeType.ALL)
    private Expert expert;
    @OneToOne(cascade = CascadeType.ALL)
    private Address address;

    @Transient
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "order", fetch = FetchType.EAGER)
    private List<Suggestion> suggestion = new ArrayList<>();


}
