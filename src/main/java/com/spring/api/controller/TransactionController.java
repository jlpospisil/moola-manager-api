package com.spring.api.controller;

import com.spring.api.exception.ResourceNotFoundException;
import com.spring.api.model.ApplicationUser;
import com.spring.api.model.Transaction;
import com.spring.api.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Validator;
import java.util.List;

@RestController
@RequestMapping("/api/transaction")
public class TransactionController {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    Validator validator;

    // List all transactions
    @GetMapping
    public List<Transaction> index(@AuthenticationPrincipal ApplicationUser authUser) {
        return transactionRepository.findAllByUserId(authUser.getId());
    }

    // Create new transaction
    @PostMapping
    public Transaction save(@RequestBody Transaction transaction, @AuthenticationPrincipal ApplicationUser authUser) {
        transaction.setUser(authUser);

        // Validate transaction before saving
        validator.validate(transaction);

        return transactionRepository.saveAndFlush(transaction);
    }

    // Get existing transaction
    @GetMapping("/{id}")
    public Transaction get(@PathVariable("id") long id, @AuthenticationPrincipal ApplicationUser authUser) throws ResourceNotFoundException {
        Transaction transaction = transactionRepository.findOneByUserId(authUser.getId(), id);

        // Get transaction if it exists and belongs to authenticated user
        if (transaction != null) {
            return transaction;
        }

        throw new ResourceNotFoundException();
    }

    // Update existing transaction
    @PutMapping("/{id}")
    public Transaction update(@PathVariable("id") long id, @RequestBody Transaction updatedTransaction, @AuthenticationPrincipal ApplicationUser authUser) throws ResourceNotFoundException {
        Transaction transaction = transactionRepository.findOneByUserId(authUser.getId(), id);

        // Update transaction if it exists and belongs to authenticated user
        if (transaction != null) {
            updatedTransaction.setId(id);
            updatedTransaction.setUser(authUser);

            // Validate transaction before saving
            validator.validate(updatedTransaction);

            return transactionRepository.saveAndFlush(updatedTransaction);
        }

        throw new ResourceNotFoundException();
    }

    // Delete existing transaction
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") long id, @AuthenticationPrincipal ApplicationUser authUser) throws ResourceNotFoundException {
        Transaction transaction = transactionRepository.findOneByUserId(authUser.getId(), id);

        // Delete transaction if it exists and belongs to authenticated user
        if (transaction != null) {
            transactionRepository.delete(transaction);
        }
        else {
            throw new ResourceNotFoundException();
        }
    }
}
