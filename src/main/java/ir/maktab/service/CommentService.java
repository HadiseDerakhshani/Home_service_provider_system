package ir.maktab.service;

import ir.maktab.data.dto.CommentDto;
import ir.maktab.data.dto.OrderDto;


public interface CommentService {


    CommentDto save(OrderDto orderDto, String text);
}
