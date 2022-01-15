package ir.maktab.data.dto;

import ir.maktab.data.model.user.Customer;
import ir.maktab.data.model.user.Expert;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.CascadeType;
import javax.persistence.OneToOne;
import java.util.Date;

@Data
public class CommentDto {
    private String commentText;
    private CustomerDto customer;
    private ExpertDto expert;
    private Date registerOpinion;
}
