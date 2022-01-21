package ir.maktab.data.dto;

import lombok.Data;

import java.util.Date;

@Data
public class CommentDto {
    private String commentText;
    private CustomerDto customer;
    private ExpertDto expert;
    private Date registerOpinion;
}
