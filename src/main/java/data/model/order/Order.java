package data.model.order;

import data.model.enums.OrderStatus;
import data.model.user.Customer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

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
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
    @ManyToOne(cascade = CascadeType.ALL)
    private Customer customer;
    @OneToOne(cascade = CascadeType.ALL)
    private Address address;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Suggestion> suggestion = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return id == order.id && Double.compare(order.ProposedPrice, ProposedPrice) == 0 && Objects.equals(jobDescription, order.jobDescription) && Objects.equals(registerDate, order.registerDate) && Objects.equals(doDate, order.doDate) && status == order.status && Objects.equals(customer, order.customer) && Objects.equals(address, order.address) && Objects.equals(suggestion, order.suggestion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, ProposedPrice, jobDescription, registerDate, doDate, status, customer, address, suggestion);
    }
}
