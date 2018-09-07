package com.spring.api.controller;

import com.spring.api.exception.ResourceNotFoundException;
import com.spring.api.model.Account;
import com.spring.api.model.ApplicationUser;
import com.spring.api.model.Transaction;
import com.spring.api.model.Merchant;
import com.spring.api.repository.TransactionRepository;
import com.spring.api.repository.MerchantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Validator;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/transaction")
public class TransactionController {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private MerchantRepository merchantRepository;

    @Autowired
    private Validator validator;

    // List all transactions
//    @GetMapping
//    public List<Transaction> index(@AuthenticationPrincipal ApplicationUser authUser) {
//        return transactionRepository.findAllByUserId(authUser.getId());
//    }

    // Create new transaction
    @PostMapping
    public Transaction save(@RequestBody Transaction transaction, @AuthenticationPrincipal ApplicationUser authUser) {
        // Get the merchantName from the request body
        Merchant merchant = transaction.getMerchant();

        if (merchant == null) {
            merchant = new Merchant();
        }

        String merchantName = merchant.getName();

        // Try to lookup that merchant
        merchant = merchantRepository.findOneByName(merchantName);

        // Create a merchant if it doesn't already exist
        if (merchant == null && merchantName != null) {
            merchant = new Merchant();
            merchant.setName(merchantName);
            merchantRepository.saveAndFlush(merchant);
        }

        // Set transaction user and merchant
        transaction.setMerchant(merchant);

        // Validate transaction before saving
        validator.validate(transaction);

        return transactionRepository.saveAndFlush(transaction);
    }

    // Get existing transaction
    @GetMapping("/{id}")
    public Transaction get(@PathVariable("id") long id, @AuthenticationPrincipal ApplicationUser authUser) throws ResourceNotFoundException {
        Optional<Transaction> transaction = transactionRepository.findById(id);
        List<Account> userAccounts = authUser.getAccounts();

        // Get transaction if it exists and belongs to authenticated user
        if (transaction.isPresent() && userAccounts.contains(transaction.get().getAccount())) {
            return transaction.get();
        }

        throw new ResourceNotFoundException();
    }

    // Update existing transaction
    @PutMapping("/{id}")
    public Transaction update(@PathVariable("id") long id, @RequestBody Transaction updatedTransaction, @AuthenticationPrincipal ApplicationUser authUser) throws ResourceNotFoundException {
        Transaction transaction = transactionRepository.findByIdAndFetchAccountEagerly(id);
        List<Account> userAccounts = authUser.getAccounts();

        // Update transaction if it exists and belongs to authenticated user
//        Account test1 = transaction.getAccount();
//        Boolean test = userAccounts.contains(transaction.getAccount());
        // TODO: figure out why .contains is failing.  probably has to do with
        if (transaction != null && userAccounts.contains(transaction.getAccount())) {
            updatedTransaction.setId(id);

            // Validate transaction before saving
            validator.validate(updatedTransaction);

            return transactionRepository.saveAndFlush(updatedTransaction);
        }

        throw new ResourceNotFoundException();
    }

    // Delete existing transaction
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") long id, @AuthenticationPrincipal ApplicationUser authUser) throws ResourceNotFoundException {
        Optional<Transaction> transaction = transactionRepository.findById(id);
        List<Account> userAccounts = authUser.getAccounts();

        // Delete transaction if it exists and belongs to authenticated user
        if (transaction.isPresent() && userAccounts.contains(transaction.get().getAccount())) {
            transactionRepository.delete(transaction.get());
        }
        else {
            throw new ResourceNotFoundException();
        }
    }
}
