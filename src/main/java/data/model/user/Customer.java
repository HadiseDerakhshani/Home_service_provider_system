package data.model.user;

import data.model.order.Order;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode
@NoArgsConstructor
@SuperBuilder
@Data
@Entity

public class Customer extends User {

    @OneToMany
    private List<Order> orderList = new ArrayList<>();


}
