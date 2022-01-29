package ir.maktab.service.implemention;

import ir.maktab.data.repasitory.CommentRepository;
import ir.maktab.data.model.order.Comment;
import ir.maktab.data.model.user.Customer;
import ir.maktab.data.model.user.Expert;
import ir.maktab.exception.ObjectEntityNotFoundException;
import ir.maktab.service.CommentService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Getter
@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    @Override
    public void save(Comment comment) {
        commentRepository.save(comment);
    }

    @Override
    public Comment createComment(Expert expert, Customer customer, String text) {
        Comment comment = Comment.builder()
                .commentText(text)
                .customer(customer)
                .expert(expert).build();

        if (comment != null)
            return comment;
        else
            throw new ObjectEntityNotFoundException("-- comment is null --");
    }
}
