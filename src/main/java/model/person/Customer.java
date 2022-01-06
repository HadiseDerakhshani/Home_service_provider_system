package model.person;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import model.Suggestion;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@SuperBuilder
@Data
@Entity
public class Customer extends User {
    private double credit;
    @OneToMany
    private List<Suggestion> suggestionList = new ArrayList<>();
}
