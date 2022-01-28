package ir.maktab.data.mapping;

import ir.maktab.data.dto.CommentDto;
import ir.maktab.data.model.order.Comment;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Data
@Component
@RequiredArgsConstructor
public class CommentMap {
    private ModelMapper mapper;

    public Comment createComment(CommentDto commentDto) {
        return mapper.map(commentDto, Comment.class);
    }

    public CommentDto createCommentDto(Comment comment) {
        return mapper.map(comment, CommentDto.class);
    }
}
