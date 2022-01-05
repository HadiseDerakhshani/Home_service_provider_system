package model.person;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import model.serviceSystem.MasterDuty;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import java.util.ArrayList;
import java.util.List;

@SuperBuilder
@NoArgsConstructor
@Data
@Entity
public class Expert extends Person {

    private byte[] image;
    private int score;
    private double credit;
    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "expertList", fetch = FetchType.EAGER)
    private List<MasterDuty> serviceList = new ArrayList<>();
}
