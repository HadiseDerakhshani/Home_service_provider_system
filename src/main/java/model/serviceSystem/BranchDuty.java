package model.serviceSystem;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import model.person.Expert;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
public class BranchDuty {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private double price;
    @ManyToMany(cascade = CascadeType.ALL)
    private List<Expert> expertList = new ArrayList<>();
    @ElementCollection
    @MapKeyColumn(name = "name")
    @Column(name = "Description")
    private Map<String, String> branchServiceMap = new HashMap<>();
}
