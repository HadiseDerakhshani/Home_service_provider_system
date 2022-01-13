package ir.maktab.service;

import ir.maktab.data.dao.CommentDao;
import ir.maktab.data.model.order.Comment;
import ir.maktab.data.model.user.Customer;
import ir.maktab.data.model.user.Expert;
import ir.maktab.exception.IsNullObjectException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Getter
@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentDao commentDao;

    public void save(Comment comment) {
        commentDao.save(comment);
    }

    public Comment createComment(Expert expert, Customer customer, String text) {
        Comment comment = Comment.builder()
                .commentText(text)
                .customer(customer)
                .expert(expert).build();

        if (comment != null)
            return comment;
        else
            throw new IsNullObjectException("-- comment is null --");
    }
}
