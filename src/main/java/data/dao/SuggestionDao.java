package data.dao;

import data.model.order.Suggestion;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SuggestionDao extends JpaRepository<Suggestion, Integer> {
    @Override
    Suggestion save(Suggestion suggestion);

    boolean exists(Suggestion suggestion);

    Suggestion findByReceptionNumber(long number);


}
