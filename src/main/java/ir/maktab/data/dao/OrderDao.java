package ir.maktab.data.dao;

import ir.maktab.data.model.enums.OrderStatus;
import ir.maktab.data.model.order.Order;
import ir.maktab.data.model.order.Suggestion;
import ir.maktab.data.model.user.Expert;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface OrderDao extends JpaRepository<Order, Integer> {
    Order save(Order order);

    List<Order> findByStatusOrStatus(OrderStatus statusSelect, OrderStatus statusSuggest);

    Order findByReceptionNumber(long number);

    @Override
    <S extends Order> long count(Example<S> example);

    @Override
    List<Order> findAll();

    @Transactional
    @Modifying
    @Query(value = "update Order set status=:status where id=:id", nativeQuery = true)
    void updateStatus(@Param("id") int id, @Param("status") OrderStatus status);

    @Transactional
    @Modifying
    @Query(value = "update Order set suggestion=:suggest where id=:id", nativeQuery = true)
    void updateSuggestion(@Param("id") int id, @Param("suggest") List<Suggestion> suggest);

    @Transactional
    @Modifying
    @Query(value = "update Order set pricePaid=:amount where  id=:id", nativeQuery = true)
    void updatePricePaid(@Param("id") int id, @Param("amount") double amount);

    @Transactional
    @Modifying
    @Query(value = "update Order set expert=:expert where  id=:id", nativeQuery = true)
    void updateExpert(@Param("id") int id, @Param("expert") Expert expert);

    @Transactional
    @Modifying
    @Query(value = "update Order set receptionNumber=:number where  id=:id", nativeQuery = true)
    void updateReceptionNumber(@Param("id") int id, @Param("number") int number);
}

