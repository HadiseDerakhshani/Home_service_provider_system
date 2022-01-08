package data.model.user;

import data.model.order.Order;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@NoArgsConstructor
@SuperBuilder
@Data
@Entity
public class Customer extends User {
    private double credit;
    @OneToMany
    private List<Order> orderList = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Customer customer = (Customer) o;
        return Double.compare(customer.credit, credit) == 0 && Objects.equals(orderList, customer.orderList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), credit, orderList);
    }
}
