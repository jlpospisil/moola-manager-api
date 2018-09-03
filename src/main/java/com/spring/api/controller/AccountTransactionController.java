package com.spring.api.controller;

import com.spring.api.exception.ResourceNotFoundException;
import com.spring.api.model.Account;
import com.spring.api.model.ApplicationUser;
import com.spring.api.model.Merchant;
import com.spring.api.model.Transaction;
import com.spring.api.repository.AccountRepository;
import com.spring.api.repository.MerchantRepository;
import com.spring.api.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Validator;
import java.util.List;

@RestController
@RequestMapping("/api/account/{account_id}/transaction")
public class AccountTransactionController {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private MerchantRepository merchantRepository;

    @Autowired
    private Validator validator;

    // Get all transactions for an account
    @GetMapping
    public List<Transaction> getTransactions(@PathVariable("account_id") long account_id, @AuthenticationPrincipal ApplicationUser authUser) throws ResourceNotFoundException {
        Account account = accountRepository.findOneByUserId(authUser.getId(), account_id);

        // Get account transactions if it exists and belongs to authenticated user
        if (account != null) {
            return account.getTransactions();
        }

        throw new ResourceNotFoundException();
    }

    // Get specific transaction for an account
    @GetMapping("/{transaction_id}")
    public Transaction getTransactions(@PathVariable("account_id") long account_id, @PathVariable("transaction_id") long transaction_id, @AuthenticationPrincipal ApplicationUser authUser) throws ResourceNotFoundException {
        Account account = accountRepository.findOneByUserId(authUser.getId(), account_id);

        // Get account transactions if it exists and belongs to authenticated user
        if (account != null) {
            Transaction transaction = transactionRepository.findOneByAccountId(account.getId(), transaction_id);

            if (transaction != null) {
                return transaction;
            }
        }

        throw new ResourceNotFoundException();
    }

    // Create new transaction
    @PostMapping
    public Transaction save(@PathVariable("account_id") long account_id, @RequestBody Transaction transaction, @AuthenticationPrincipal ApplicationUser authUser) throws ResourceNotFoundException {
        Account account = accountRepository.findOneByUserId(authUser.getId(), account_id);

        if (account != null) {
            // Get the merchant if it exists
            String merchantName = "test";   // TODO: get this from request body
            Merchant merchant = merchantRepository.findOneByName(merchantName);

            // Create a merchant otherwise
            if (merchant == null) {
                merchant = new Merchant();
                merchant.setName(merchantName);
                merchantRepository.saveAndFlush(merchant);
            }

            // Set transaction user, account, and merchant
            transaction.setUser(authUser);
            transaction.setAccount(account);
            transaction.setMerchant(merchant);

            // Validate transaction before saving
            validator.validate(transaction);

            return transactionRepository.saveAndFlush(transaction);
        }

        throw new ResourceNotFoundException();
    }

    // Update existing transaction
    @PutMapping("/{transaction_id}")
    public Transaction update(@PathVariable("account_id") long account_id, @PathVariable("transaction_id") long transaction_id, @RequestBody Transaction updatedTransaction, @AuthenticationPrincipal ApplicationUser authUser) throws ResourceNotFoundException {
        Account account = accountRepository.findOneByUserId(authUser.getId(), account_id);

        // Get account transactions if it exists and belongs to authenticated user
        if (account != null) {
            Transaction transaction = transactionRepository.findOneByAccountId(account.getId(), transaction_id);

            if (transaction != null) {
                updatedTransaction.setId(transaction.getId());
                updatedTransaction.setUser(authUser);
                updatedTransaction.setAccount(account);

                // Validate transaction before saving
                validator.validate(updatedTransaction);

                return transactionRepository.saveAndFlush(updatedTransaction);
            }
        }

        throw new ResourceNotFoundException();
    }

    // Get specific transaction for an account
    @DeleteMapping("/{transaction_id}")
    public void delete(@PathVariable("account_id") long account_id, @PathVariable("transaction_id") long transaction_id, @AuthenticationPrincipal ApplicationUser authUser) throws ResourceNotFoundException {
        Account account = accountRepository.findOneByUserId(authUser.getId(), account_id);

        // Get account transactions if it exists and belongs to authenticated user
        if (account != null) {
            Transaction transaction = transactionRepository.findOneByAccountId(account.getId(), transaction_id);

            if (transaction != null) {
                transactionRepository.delete(transaction);
                return;
            }
        }

        throw new ResourceNotFoundException();
    }
}
