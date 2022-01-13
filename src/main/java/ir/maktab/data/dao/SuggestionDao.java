package ir.maktab.data.dao;

import ir.maktab.data.model.enums.SuggestionStatus;
import ir.maktab.data.model.order.Suggestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface SuggestionDao extends JpaRepository<Suggestion, Integer> {
    @Override
    Suggestion save(Suggestion suggestion);

    Suggestion findByReceptionNumber(long number);

    @Transactional
    @Modifying
    @Query(value = "update Suggestion set receptionNumber=:number where  id=:id", nativeQuery = true)
    void updateReceptionNumber(@Param("id") int id, @Param("number") int number);

    @Transactional
    @Modifying
    @Query(value = "update Suggestion set status=:status where  id=:id", nativeQuery = true)
    void updateStatus(@Param("id") int id, @Param("status") SuggestionStatus status);
}
