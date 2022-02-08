package ir.maktab.data.mapping;

import ir.maktab.data.dto.TransactionDto;
import ir.maktab.data.entity.order.Transaction;
import lombok.Data;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Data
@Component

public class TransactionMap {
    private final ModelMapper mapper;
    private final OrderMap orderMap;

    @Autowired
    public TransactionMap(ModelMapper mapper, @Lazy OrderMap orderMap) {
        this.mapper = mapper;
        this.orderMap = orderMap;
    }

    public Transaction createTransaction(TransactionDto transactionDto) {
        Transaction transaction = Transaction.builder()
                .amount(transactionDto.getAmount())
                .transactionNumber(transactionDto.getTransactionNumber())
                .registerDate(transactionDto.getRegisterDate()).build();
        if (transactionDto.getOrder() != null) {
            transaction.setOrder(orderMap.createOrder(transactionDto.getOrder()));
        }
        return transaction;
    }

    public TransactionDto createTransactionDto(Transaction transaction) {
        TransactionDto transactionDto = TransactionDto.builder()
                .amount(transaction.getAmount())
                .transactionNumber(transaction.getTransactionNumber())
                .registerDate(transaction.getRegisterDate()).build();
        if (transaction.getOrder() != null) {
            transactionDto.setOrder(orderMap.createOrderDto(transaction.getOrder()));
        }
        return transactionDto;
    }
}
