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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@EqualsAndHashCode
@SuperBuilder
@NoArgsConstructor
@Data
@Entity
public class Expert extends User {

    @Lob
    private byte[] photo;
    private int score;
     @Transient
    @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private Set<SubService> serviceList = new HashSet<>();
   @Transient
    @OneToMany(mappedBy = "expert",fetch = FetchType.EAGER)
    private List<Order> orderList = new ArrayList<>();
     @Transient
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private Set<Comment> commentList = new HashSet<>();

}
