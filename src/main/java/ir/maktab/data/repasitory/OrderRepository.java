package ir.maktab.data.repasitory;

import ir.maktab.data.entity.enums.OrderStatus;
import ir.maktab.data.entity.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

    List<Order> findByStatusOrStatus(OrderStatus statusSelect, OrderStatus statusSuggest);

    Optional<Order> findByReceptionNumber(long number);


}

