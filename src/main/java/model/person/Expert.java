package model.person;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;

@SuperBuilder
@NoArgsConstructor
@Data
@Entity
public class Expert extends Person {
    //  private Image image;
    private byte[] image;
    private int score;
    private double credit;
    // private List<Service> serviceList=new ArrayList<>();
}
