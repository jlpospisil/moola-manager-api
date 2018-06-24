package com.spring.api.controller;

import com.spring.api.model.ApplicationUser;
import com.spring.api.model.Item;
import com.spring.api.exception.ResourceNotFoundException;
import com.spring.api.repository.ItemRepository;
import com.spring.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/test")
public class ItemController {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private UserRepository userRepository;

    // List all items
    @GetMapping
    public List<Item> index(@AuthenticationPrincipal ApplicationUser authUser) {
        return itemRepository.findbyUserId(authUser.getId());
    }

    // Create new item
    @PostMapping
    public Item save(@Valid @RequestBody Item test) {
        return itemRepository.saveAndFlush(test);
    }

    // Get existing item
    @GetMapping("/{id}")
    public Item get(@PathVariable("id") long id) throws ResourceNotFoundException {
        Optional<Item> currentTest = itemRepository.findById(id);

        if (currentTest.isPresent()) {
            return currentTest.get();
        }

        throw new ResourceNotFoundException();
    }

    // Update existing item
    @PutMapping("/{id}")
    public Item update(@PathVariable("id") long id, @Valid @RequestBody Item updatedTest) throws ResourceNotFoundException {
        Optional<Item> currentTest = itemRepository.findById(id);

        if (currentTest.isPresent()) {
            updatedTest.setId(id);
            return itemRepository.saveAndFlush(updatedTest);
        }

        throw new ResourceNotFoundException();
    }

    // Delete existing item
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") long id) throws ResourceNotFoundException {
        Optional<Item> currentTest = itemRepository.findById(id);

        if (currentTest.isPresent()) {
            itemRepository.delete(currentTest.get());
        }
        else {
            throw new ResourceNotFoundException();
        }
    }
}
