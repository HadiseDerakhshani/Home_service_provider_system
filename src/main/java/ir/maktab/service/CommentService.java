package ir.maktab.service;

import ir.maktab.data.dto.CommentDto;
import ir.maktab.data.dto.OrderDto;
import ir.maktab.data.entity.order.Comment;
import ir.maktab.data.entity.user.Customer;
import ir.maktab.data.entity.user.Expert;


public interface CommentService {




    public CommentDto save(OrderDto orderDto, String text);
}
