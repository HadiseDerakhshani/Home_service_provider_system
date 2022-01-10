package data.model.user;


import data.model.order.Order;
import data.model.serviceSystem.SubService;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode
@SuperBuilder
@NoArgsConstructor
@Data
@Entity
public class Expert extends User {

    private byte[] image;
    private int score;
    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "expertList", fetch = FetchType.EAGER)
    private List<SubService> serviceList = new ArrayList<>();
    @OneToMany
    private List<Order> orderList = new ArrayList<>();


}
