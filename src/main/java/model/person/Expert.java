package model.person;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import model.Suggestion;
import model.serviceSystem.MasterDuty;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@SuperBuilder
@NoArgsConstructor
@Data
@Entity
public class Expert extends User {

    private byte[] image;
    private int score;
    private double credit;
    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "expertList", fetch = FetchType.EAGER)
    private List<MasterDuty> serviceList = new ArrayList<>();
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "expert")
    private List<Suggestion> suggestion;
}
