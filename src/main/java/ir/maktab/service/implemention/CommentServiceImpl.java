package ir.maktab.service.implemention;

import ir.maktab.data.dto.CommentDto;
import ir.maktab.data.dto.OrderDto;
import ir.maktab.data.entity.order.Comment;
import ir.maktab.data.entity.order.Order;
import ir.maktab.data.entity.user.Customer;
import ir.maktab.data.entity.user.Expert;
import ir.maktab.data.mapping.CommentMap;
import ir.maktab.data.repasitory.CommentRepository;
import ir.maktab.exception.ObjectEntityNotFoundException;
import ir.maktab.service.CommentService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Getter
@Service

public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final CommentMap commentMap;
    private final OrderServiceImpl orderService;
@Autowired
    public CommentServiceImpl(CommentRepository commentRepository, @Lazy CommentMap commentMap,@Lazy OrderServiceImpl orderService) {
        this.commentRepository = commentRepository;
        this.commentMap = commentMap;
        this.orderService = orderService;
    }

    @Override
    public CommentDto save(OrderDto orderDto, String text) {
        Order order = orderService.findByReceptionNumber(orderDto.getReceptionNumber());
        Comment comment = Comment.builder()
                .commentText(text)
                .customer(order.getCustomer())
                .expert(order.getExpert()).build();

            return commentMap.createCommentDto(comment);

    }
}
