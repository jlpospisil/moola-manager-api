package com.spring.api.controller;

import com.spring.api.model.ApplicationUser;
import com.spring.api.model.Account;
import com.spring.api.exception.ResourceNotFoundException;
import com.spring.api.model.Transaction;
import com.spring.api.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/account")
public class AccountController {

    @Autowired
    private AccountRepository accountRepository;

    // List all accounts
    @GetMapping
    public List<Account> index(@AuthenticationPrincipal ApplicationUser authUser) {
        return accountRepository.findAllByUserId(authUser.getId());
    }

    // Create new account
    @PostMapping
    public Account save(@Valid @RequestBody Account details, @AuthenticationPrincipal ApplicationUser authUser) {
        Account account = accountRepository.saveAndFlush(details);
        account.getUsers().add(authUser);
        return accountRepository.saveAndFlush(account);
    }

    // Get existing account
    @GetMapping("/{id}")
    public Account get(@PathVariable("id") long id, @AuthenticationPrincipal ApplicationUser authUser) throws ResourceNotFoundException {
        Account account = accountRepository.findOneByUserId(authUser.getId(), id);

        // Get account if it exists and belongs to authenticated user
        if (account != null) {
            return account;
        }

        throw new ResourceNotFoundException();
    }

    // Update existing account
    @PutMapping("/{id}")
    public Account update(@PathVariable("id") long id, @Valid @RequestBody Account updatedAccount, @AuthenticationPrincipal ApplicationUser authUser) throws ResourceNotFoundException {
        Account account = accountRepository.findOneByUserId(authUser.getId(), id);

        // Update account if it exists and belongs to authenticated user
        if (account != null) {
            updatedAccount.setId(id);
            updatedAccount.setUsers(account.getUsers());
            return accountRepository.saveAndFlush(updatedAccount);
        }

        throw new ResourceNotFoundException();
    }

    // Delete existing account
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") long id, @AuthenticationPrincipal ApplicationUser authUser) throws ResourceNotFoundException {
        Account account = accountRepository.findOneByUserId(authUser.getId(), id);

        // Delete account if it exists and belongs to authenticated user
        if (account != null) {
            // accountRepository.delete(account); // this was deleting associated transactions and users (even with soft delete implemented)
            account.setDeleted_at(new Date());
            accountRepository.saveAndFlush(account);
        }
        else {
            throw new ResourceNotFoundException();
        }
    }
}
