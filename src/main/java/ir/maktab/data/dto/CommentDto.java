package ir.maktab.data.dto;

import lombok.Data;

import java.util.Date;

@Data
public class CommentDto {
    private String commentText;
    private Date registerOpinion;

}
