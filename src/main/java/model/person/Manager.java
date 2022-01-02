package model.person;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;


@NoArgsConstructor
@SuperBuilder
@Data
@Entity
public class Manager extends Person {

    private String username;
    private String password;

}
