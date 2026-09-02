package com.example.transactionstarter.transaction;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public Transaction createTransaction(Transaction transaction) {

        if (transaction.getTransactionStatus() != TransactionStatus.PENDING) {
            throw new IllegalArgumentException(
                    "New transaction must have PENDING status"
            );
        }

        if (transactionRepository.existsById(transaction.getTransactionId())) {
            throw new IllegalArgumentException(
                    "Transaction already exists"
            );
        }

        return transactionRepository.save(transaction);
    }

    public Transaction getTransaction(String transactionId) {

        return transactionRepository.findById(transactionId)                		
                		.orElseThrow(() -> new TransactionNotFoundException(
                		        "Transaction not found"
                		));
                
    }

    public Transaction updateStatus(
            String transactionId,
            TransactionStatus newStatus) {

        Transaction transaction = getTransaction(transactionId);

        if (newStatus == null) {
            throw new IllegalArgumentException(
                    "New status cannot be null"
            );
        }

        if (transaction.getTransactionStatus() != TransactionStatus.PENDING) {
            throw new IllegalArgumentException(
                    "Only PENDING transactions can be updated"
            );
        }

        if (newStatus == TransactionStatus.PENDING) {
            throw new IllegalArgumentException(
                    "Transaction is already PENDING"
            );
        }

        transaction.setTransactionStatus(newStatus);

        return transactionRepository.save(transaction);
    }

    public List<Transaction> getCustomerTransactions(String customerId) {

        return transactionRepository.findByCustomerId(customerId);
    }
}