package model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import model.enums.OrderStatus;
import model.person.Customer;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
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
    private OrderStatus status;
    @ManyToOne(cascade = CascadeType.ALL)
    private Customer customer;
    @OneToOne(cascade = CascadeType.ALL)
    private Address address;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Suggestion> suggestion;

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
