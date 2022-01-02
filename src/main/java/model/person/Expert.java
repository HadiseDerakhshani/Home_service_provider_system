package model.person;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import java.awt.*;
import java.util.ArrayList;
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Expert extends Person {
  //  private Image image;
    private byte[] image;
    private int score;
    private String opinion;
   // private List<Service> serviceList=new ArrayList<>();
}
