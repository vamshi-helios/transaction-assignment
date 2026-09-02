package com.example.transactionstarter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.transactionstarter.transaction.Transaction;
import com.example.transactionstarter.transaction.TransactionRepository;
import com.example.transactionstarter.transaction.TransactionService;
import com.example.transactionstarter.transaction.TransactionStatus;
import com.example.transactionstarter.transaction.TransactionType;

import org.springframework.transaction.TransactionSystemException;

@SpringBootTest
class TransactionStarterApplicationTests {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private TransactionRepository transactionRepository;

    @BeforeEach
    void setUp() {
        transactionRepository.deleteAll();
    }

    @Test
    void contextLoads() {
    }

    @Test
    void shouldCreateTransaction() {

        Transaction transaction = new Transaction();

        transaction.setTransactionId("TXN001");
        transaction.setCustomerId("CUST001");
        transaction.setAmount(new BigDecimal("1500.00"));
        transaction.setCurrency("INR");
        transaction.setTransactionType(TransactionType.PAYMENT);
        transaction.setTransactionStatus(TransactionStatus.PENDING);

        Transaction savedTransaction =
                transactionService.createTransaction(transaction);

        assertEquals("TXN001", savedTransaction.getTransactionId());
        assertEquals("CUST001", savedTransaction.getCustomerId());
        assertEquals(TransactionStatus.PENDING,
                savedTransaction.getTransactionStatus());
    }
    
    @Test
    void shouldGetTransaction() {

        Transaction transaction = new Transaction();

        transaction.setTransactionId("TXN002");
        transaction.setCustomerId("CUST002");
        transaction.setAmount(new BigDecimal("2500.00"));
        transaction.setCurrency("INR");
        transaction.setTransactionType(TransactionType.PAYMENT);
        transaction.setTransactionStatus(TransactionStatus.PENDING);

        transactionService.createTransaction(transaction);

        Transaction foundTransaction =
                transactionService.getTransaction("TXN002");

        assertEquals("TXN002", foundTransaction.getTransactionId());
        assertEquals("CUST002", foundTransaction.getCustomerId());
        assertEquals(new BigDecimal("2500.00"),
                foundTransaction.getAmount());
    }
    
    @Test
    void shouldUpdateTransactionStatus() {

        Transaction transaction = new Transaction();

        transaction.setTransactionId("TXN003");
        transaction.setCustomerId("CUST003");
        transaction.setAmount(new BigDecimal("3000.00"));
        transaction.setCurrency("INR");
        transaction.setTransactionType(TransactionType.PAYMENT);
        transaction.setTransactionStatus(TransactionStatus.PENDING);

        transactionService.createTransaction(transaction);

        Transaction updatedTransaction =
                transactionService.updateStatus("TXN003", TransactionStatus.COMPLETED);

        assertEquals(TransactionStatus.COMPLETED,
                updatedTransaction.getTransactionStatus());
    }
    
    @Test
    void shouldGetCustomerTransactions() {

        Transaction transaction1 = new Transaction();

        transaction1.setTransactionId("TXN004");
        transaction1.setCustomerId("CUST004");
        transaction1.setAmount(new BigDecimal("1000.00"));
        transaction1.setCurrency("INR");
        transaction1.setTransactionType(TransactionType.PAYMENT);
        transaction1.setTransactionStatus(TransactionStatus.PENDING);

        Transaction transaction2 = new Transaction();

        transaction2.setTransactionId("TXN005");
        transaction2.setCustomerId("CUST004");
        transaction2.setAmount(new BigDecimal("2000.00"));
        transaction2.setCurrency("INR");
        transaction2.setTransactionType(TransactionType.REFUND);
        transaction2.setTransactionStatus(TransactionStatus.PENDING);

        transactionService.createTransaction(transaction1);
        transactionService.createTransaction(transaction2);

        List<Transaction> transactions =
                transactionService.getCustomerTransactions("CUST004");

        assertEquals(2, transactions.size());
    }
    
    @Test
    void shouldRejectStatusUpdateForCompletedTransaction() {

        Transaction transaction = new Transaction();

        transaction.setTransactionId("TXN006");
        transaction.setCustomerId("CUST006");
        transaction.setAmount(new BigDecimal("4000.00"));
        transaction.setCurrency("INR");
        transaction.setTransactionType(TransactionType.PAYMENT);
        transaction.setTransactionStatus(TransactionStatus.PENDING);

        transactionService.createTransaction(transaction);

        transactionService.updateStatus(
                "TXN006",
                TransactionStatus.COMPLETED
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> transactionService.updateStatus(
                        "TXN006",
                        TransactionStatus.PENDING
                )
        );
    }
    
    @Test
    void shouldRejectInvalidAmount() {

        Transaction transaction = new Transaction();

        transaction.setTransactionId("TXN007");
        transaction.setCustomerId("CUST007");
        transaction.setAmount(new BigDecimal("0.00"));
        transaction.setCurrency("INR");
        transaction.setTransactionType(TransactionType.PAYMENT);
        transaction.setTransactionStatus(TransactionStatus.PENDING);

        assertThrows(
                TransactionSystemException.class,
                () -> transactionService.createTransaction(transaction)
        );
    }

}