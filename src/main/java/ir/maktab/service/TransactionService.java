package ir.maktab.service;


import ir.maktab.data.dto.OrderDto;
import ir.maktab.data.dto.TransactionDto;
import ir.maktab.data.entity.order.Transaction;

import java.util.Optional;

public interface TransactionService {
   Optional<Transaction> findByReceptionNumber(long number);

   TransactionDto save(OrderDto orderDto, double amount);

   Transaction giveTransactionNumber(Transaction transaction);
}
