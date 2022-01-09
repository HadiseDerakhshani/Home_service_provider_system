package data.dao;

import data.model.order.Suggestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SuggestionDao extends JpaRepository<Suggestion, Integer> {
    @Override
    Suggestion save(Suggestion suggestion);

    boolean exists(Suggestion suggestion);
}
