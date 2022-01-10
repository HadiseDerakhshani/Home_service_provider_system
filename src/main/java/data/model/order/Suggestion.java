package data.model.order;

import data.model.enums.SuggestionStatus;
import data.model.user.Expert;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@EqualsAndHashCode
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
    private long receptionNumber;
    @Enumerated(EnumType.STRING)
    private SuggestionStatus status;
    @ManyToOne
    private Expert expert;
    @ManyToOne
    private Order order;

}
