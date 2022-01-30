package ir.maktab.service;

import ir.maktab.data.entity.order.Comment;
import ir.maktab.data.entity.user.Customer;
import ir.maktab.data.entity.user.Expert;


public interface CommentService {


    public void save(Comment comment);

    public Comment createComment(Expert expert, Customer customer, String text);
}
