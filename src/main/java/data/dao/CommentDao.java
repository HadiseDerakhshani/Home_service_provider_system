package data.dao;

import data.model.order.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentDao extends JpaRepository<Comment,Integer> {
    @Override
    Comment  save(Comment comment);
}
