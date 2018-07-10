package com.spring.api.controller;

import com.spring.api.model.ApplicationUser;
import com.spring.api.model.Account;
import com.spring.api.exception.ResourceNotFoundException;
import com.spring.api.repository.AccountRepository;
import com.spring.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/account")
public class AccountController {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private UserRepository userRepository;

    // List all accounts
    @GetMapping
    public List<Account> index(@AuthenticationPrincipal ApplicationUser authUser) {
        return accountRepository.findbyUserId(authUser.getId());
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
    public Account get(@PathVariable("id") long id) throws ResourceNotFoundException {
        // TODO: make sure account belongs to authUser

        Optional<Account> currentTest = accountRepository.findById(id);

        if (currentTest.isPresent()) {
            return currentTest.get();
        }

        throw new ResourceNotFoundException();
    }

    // Update existing account
    @PutMapping("/{id}")
    public Account update(@PathVariable("id") long id, @Valid @RequestBody Account updatedTest) throws ResourceNotFoundException {
        // TODO: make sure account belongs to authUser

        Optional<Account> currentTest = accountRepository.findById(id);

        if (currentTest.isPresent()) {
            updatedTest.setId(id);
            return accountRepository.saveAndFlush(updatedTest);
        }

        throw new ResourceNotFoundException();
    }

    // Delete existing account
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") long id) throws ResourceNotFoundException {
        // TODO: make sure account belongs to authUser

        Optional<Account> currentTest = accountRepository.findById(id);

        if (currentTest.isPresent()) {
            accountRepository.delete(currentTest.get());
        }
        else {
            throw new ResourceNotFoundException();
        }
    }
}
