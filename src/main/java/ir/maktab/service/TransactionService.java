package ir.maktab.service;


import ir.maktab.data.dto.OrderDto;
import ir.maktab.data.dto.TransactionDto;
import ir.maktab.data.entity.order.Order;
import ir.maktab.data.entity.order.Transaction;

import java.util.Optional;

public interface TransactionService {
    public Optional<Transaction> findByReceptionNumber(long number);

    public TransactionDto save(OrderDto orderDto, double amount);

    public Transaction giveReceptionNumber(Transaction transaction);
}
