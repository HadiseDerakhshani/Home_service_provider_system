package service;

import data.dao.CommentDao;
import data.model.order.Comment;
import data.model.user.Customer;
import data.model.user.Expert;
import exception.IsNullObjectException;
import org.springframework.stereotype.Service;

@Service
public class CommentService extends BaseService {
    private CommentDao commentDao;

    public CommentService(CommentDao commentDao) {
        this.commentDao = commentDao;
    }
    public void save(Comment comment){
        commentDao.save(comment);
    }
    public Comment createComment(Expert expert, Customer customer,String text){
        Comment comment=Comment.builder()
                .commentText(text)
                .customer(customer)
                .expert(expert).build();

        if (comment!=null)
            return comment;
        else
            throw new IsNullObjectException("-- comment is null --");
    }
}
