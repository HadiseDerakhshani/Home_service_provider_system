package ir.maktab.data.model.serviceSystem;

import ir.maktab.data.model.user.Expert;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
public class SubService {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private double price;
    private String name;
    private String description;
    @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<Expert> expertList = new ArrayList<>();

}
