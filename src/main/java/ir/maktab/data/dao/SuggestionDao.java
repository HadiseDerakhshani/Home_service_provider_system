package ir.maktab.data.dao;

import ir.maktab.data.model.order.Suggestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SuggestionDao extends JpaRepository<Suggestion, Integer> {

    Optional<Suggestion> findByReceptionNumber(long number);

   /* @Transactional
    @Modifying
    @Query(value = "update Suggestion set receptionNumber=:number where  id=:id")
    void updateReceptionNumber(@Param("id") int id, @Param("number") int number);

    @Transactional
    @Modifying
    @Query(value = "update Suggestion set status=:status where  id=:id")
    void updateStatus(@Param("id") int id, @Param("status") SuggestionStatus status);*/
}
