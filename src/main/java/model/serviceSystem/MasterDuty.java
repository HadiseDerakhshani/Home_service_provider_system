package model.serviceSystem;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import model.person.Expert;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
public class MasterDuty {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    @OneToMany(cascade = CascadeType.ALL)
    private List<BranchDuty> branchDutyList = new ArrayList<>();
    @ManyToMany(cascade = CascadeType.ALL)
    private List<Expert> expertList = new ArrayList<>();
}
