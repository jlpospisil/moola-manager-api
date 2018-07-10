package com.spring.api.controller;

import com.spring.api.exception.ResourceNotFoundException;
import com.spring.api.model.Account;
import com.spring.api.model.ApplicationUser;
import com.spring.api.model.Transaction;
import com.spring.api.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/account/{id}/transaction")
public class AccountTransactionController {

    @Autowired
    private AccountRepository accountRepository;

    // Get all transactions for an account
    @GetMapping
    public List<Transaction> getTransactions(@PathVariable("id") long id, @AuthenticationPrincipal ApplicationUser authUser) throws ResourceNotFoundException {
        Account account = accountRepository.findOneByUserId(authUser.getId(), id);

        // Get account if it exists and belongs to authenticated user
        if (account != null) {
            return account.getTransactions();
        }

        throw new ResourceNotFoundException();
    }
}
