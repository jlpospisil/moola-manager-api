package com.spring.api.controller;

import com.spring.api.domain.Test;
import com.spring.api.exception.ResourceNotFoundException;
import com.spring.api.repository.TestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/test")
public class TestController {

    @Autowired
    private TestRepository testRepository;

    // List all items
    @GetMapping
    public List index() {
        return testRepository.findAll();
    }

    // Create new item
    @PostMapping
    public Test save(@Valid @RequestBody Test test) {
        return testRepository.saveAndFlush(test);
    }

    // Get existing item
    @GetMapping("/{id}")
    public Test get(@PathVariable("id") long id) throws ResourceNotFoundException {
        Optional<Test> currentTest = testRepository.findById(id);

        if (currentTest.isPresent()) {
            return currentTest.get();
        }

        throw new ResourceNotFoundException();
    }

    // Update existing item
    @PutMapping("/{id}")
    public Test update(@PathVariable("id") long id, @Valid @RequestBody Test updatedTest) throws ResourceNotFoundException {
        Optional<Test> currentTest = testRepository.findById(id);

        if (currentTest.isPresent()) {
            updatedTest.setId(id);
            return testRepository.saveAndFlush(updatedTest);
        }

        throw new ResourceNotFoundException();
    }

    // Delete existing item
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") long id) throws ResourceNotFoundException {
        Optional<Test> currentTest = testRepository.findById(id);

        if (currentTest.isPresent()) {
            testRepository.delete(currentTest.get());
        }
        else {
            throw new ResourceNotFoundException();
        }
    }
}
