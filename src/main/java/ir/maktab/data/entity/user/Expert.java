package ir.maktab.data.entity.user;


import ir.maktab.data.entity.order.Comment;
import ir.maktab.data.entity.order.Order;
import ir.maktab.data.entity.serviceSystem.SubService;
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

    @Lob
    private byte[] photo;
    private int score;
    @ManyToMany(cascade = CascadeType.ALL)
    private List<SubService> serviceList = new ArrayList<>();
    @OneToMany(mappedBy = "expert")
    private List<Order> orderList = new ArrayList<>();
    @OneToMany(cascade = CascadeType.ALL)
    private List<Comment> commentList = new ArrayList<>();

}
