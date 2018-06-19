package com.spring.api.controller;

import com.spring.api.model.Item;
import com.spring.api.exception.ResourceNotFoundException;
import com.spring.api.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/test")
public class ItemController {

    @Autowired
    private ItemRepository itemRepository;

    // List all items
    @GetMapping
    public List index() {
        return itemRepository.findAll();
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
