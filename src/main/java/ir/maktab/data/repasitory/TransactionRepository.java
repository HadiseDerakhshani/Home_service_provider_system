package ir.maktab.data.repasitory;

import ir.maktab.data.entity.order.Address;
import ir.maktab.data.entity.order.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

}
