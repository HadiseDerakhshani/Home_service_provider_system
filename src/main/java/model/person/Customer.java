package model.person;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;

@NoArgsConstructor
@SuperBuilder
@Data
@Entity
public class Customer extends Person {
    private double credit;
}
