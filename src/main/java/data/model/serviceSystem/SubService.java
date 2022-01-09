package data.model.serviceSystem;

import data.model.user.Expert;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
public class SubService {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private double price;

    @ElementCollection
    @MapKeyColumn(name = "name")
    @Column(name = "Description")
    private Map<String, String> subServiceMap = new HashMap<>();
    @ManyToMany(cascade = CascadeType.ALL)
    private List<Expert> expertList = new ArrayList<>();
}
