package ir.maktab.data.model.user;


import ir.maktab.data.model.order.Comment;
import ir.maktab.data.model.order.Order;
import ir.maktab.data.model.order.Suggestion;
import ir.maktab.data.model.serviceSystem.SubService;
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
    private byte[] image;
    private int score;
    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "expertList", fetch = FetchType.EAGER)
    private List<SubService> serviceList = new ArrayList<>();
    @OneToMany
    private List<Order> orderList = new ArrayList<>();
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "expert")
    private List<Suggestion> suggestList = new ArrayList<>();
    @OneToMany(cascade = CascadeType.ALL)
    private List<Comment> commentList = new ArrayList<>();

}
