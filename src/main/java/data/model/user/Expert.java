package data.model.user;


import data.model.serviceSystem.SubService;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode
@SuperBuilder
@NoArgsConstructor
@Data
@Entity
public class Expert extends User {

    private byte[] image;
    private int score;
    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "expertList", fetch = FetchType.EAGER)
    private List<SubService> serviceList = new ArrayList<>();


}
