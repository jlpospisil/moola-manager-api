package com.spring.api.controller;

import com.spring.api.model.ApplicationUser;
import com.spring.api.exception.UnprocessableEntityException;
import com.spring.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping
    public void create(@Valid @RequestBody ApplicationUser user, BCryptPasswordEncoder encoder) throws UnprocessableEntityException {
        user.setPassword(encoder.encode(user.getPassword()));

        try {
            userRepository.saveAndFlush(user);

        } catch (DataIntegrityViolationException e) {
            throw new UnprocessableEntityException("The slected username is already taken");
        }
    }
}
