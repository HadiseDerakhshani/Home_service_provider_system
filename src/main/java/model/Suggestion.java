package model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import model.person.Expert;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Suggestion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @CreationTimestamp
    private Date dateRegisterSuggest;
    private double ProposedPrice;
    private int durationOfWork;
    private int startTime;
    @ManyToOne
    private Expert expert;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Suggestion that = (Suggestion) o;
        return id == that.id && Double.compare(that.ProposedPrice, ProposedPrice) == 0 && durationOfWork == that.durationOfWork && startTime == that.startTime && Objects.equals(dateRegisterSuggest, that.dateRegisterSuggest) && Objects.equals(expert, that.expert);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, dateRegisterSuggest, ProposedPrice, durationOfWork, startTime, expert);
    }
}
