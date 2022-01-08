package data.model.serviceSystem;

import data.model.user.Expert;
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
public class Service {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    @OneToMany(cascade = CascadeType.ALL)
    private List<SubService> subServiceList = new ArrayList<>();
    @ManyToMany(cascade = CascadeType.ALL)
    private List<Expert> expertList = new ArrayList<>();
}
