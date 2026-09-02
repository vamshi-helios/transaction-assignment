package com.example.transactionstarter.transaction;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;



import jakarta.validation.Valid;

import java.util.List;

@RestController
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping("/api/transactions/{transactionId}")
    public Transaction getTransaction(@PathVariable String transactionId) {

        return transactionService.getTransaction(transactionId);
    }

    @PostMapping("/api/transactions")
    public Transaction createTransaction(@Valid @RequestBody Transaction transaction) {

        return transactionService.createTransaction(transaction);
    }

    @PatchMapping("/api/transactions/{transactionId}/status")
    public Transaction updateStatus(
            @PathVariable String transactionId,
            @RequestBody TransactionStatus newStatus) {

        return transactionService.updateStatus(transactionId, newStatus);
    }

    @GetMapping("/api/customers/{customerId}/transactions")
    public List<Transaction> getCustomerTransactions(
            @PathVariable String customerId) {

        return transactionService.getCustomerTransactions(customerId);
    }
    
    
}