package ir.maktab.data.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CommentDto {
    private String commentText;
    private CustomerDto customer;
    private ExpertDto expert;
    @CreationTimestamp
    private Date registerComment;
}
