package model.person;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Manager extends Person{
    private String username;
    private String password;

}
