package ir.maktab.service.implemention;

import ir.maktab.data.dto.OrderDto;
import ir.maktab.data.dto.TransactionDto;
import ir.maktab.data.entity.order.Order;
import ir.maktab.data.entity.order.Transaction;
import ir.maktab.data.mapping.OrderMap;
import ir.maktab.data.mapping.TransactionMap;
import ir.maktab.data.repasitory.TransactionRepository;
import ir.maktab.service.TransactionService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Getter
@Service

public class TransactionServiceImpl implements TransactionService {
 private final TransactionRepository transactionRepository;
 private final OrderMap orderMap;
 private final TransactionMap transactionMap;
@Autowired
    public TransactionServiceImpl(TransactionRepository transactionRepository,@Lazy OrderMap orderMap,
                                  @Lazy TransactionMap transactionMap) {
        this.transactionRepository = transactionRepository;
        this.orderMap = orderMap;
        this.transactionMap=transactionMap;
    }

    @Override
    public Optional<Transaction> findByReceptionNumber(long number) {
        return transactionRepository.findByReceptionNumber(number);
    }

    @Override
    public TransactionDto save(OrderDto orderDto, double amount) {
        Transaction transaction=Transaction.builder()
                .amount(amount)
                .order(orderMap.createOrder(orderDto)).build();
        Transaction saveTransaction = transactionRepository.save(transaction);

        return  transactionMap.createTransactionDto(transactionRepository.save(giveReceptionNumber(transaction)));
    }

    @Override
    public Transaction giveReceptionNumber(Transaction transaction) {
        transaction.setReceptionNumber(1000 + transaction.getId());
        return transaction;
    }
}
