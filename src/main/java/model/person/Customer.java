package model.person;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import model.Order;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@SuperBuilder
@Data
@Entity
public class Customer extends User {
    private double credit;
    @OneToMany
    private List<Order> orderList = new ArrayList<>();
}
