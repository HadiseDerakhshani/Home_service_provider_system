package model.person;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;

@NoArgsConstructor
@SuperBuilder
@Data
@Entity
public class Customer extends User {
    private double credit;
}
