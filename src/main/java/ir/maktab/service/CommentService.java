package ir.maktab.service;

import ir.maktab.data.model.order.Comment;
import ir.maktab.data.model.user.Customer;
import ir.maktab.data.model.user.Expert;


public interface CommentService {


    public void save(Comment comment);

    public Comment createComment(Expert expert, Customer customer, String text);
}
