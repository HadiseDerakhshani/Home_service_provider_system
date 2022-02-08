package ir.maktab.data.repasitory;


import ir.maktab.data.entity.order.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

    Optional<Transaction> findByTransactionNumber(long number);
}
