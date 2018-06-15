package com.spring.test.controller;

import com.spring.test.domain.Test;
import com.spring.test.http.exception.ResourceNotFoundException;
import com.spring.test.repository.TestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
public class TestController {

    @Autowired
    private TestRepository testRepository;

    // List all items
    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public List index() {
        return testRepository.findAll();
    }

    // Create new item
    @RequestMapping(value = "/test", method = RequestMethod.POST)
    public Test save(@Valid @RequestBody Test test) {
        return testRepository.saveAndFlush(test);
    }

    // Get existing item
    @RequestMapping(value = "/test/{id}", method = RequestMethod.GET)
    public Test get(@PathVariable("id") long id) {
        Optional<Test> currentTest = testRepository.findById(id);

        if (currentTest.isPresent()) {
            return currentTest.get();
        }

        throw new ResourceNotFoundException();
    }

    // Update existing item
    @RequestMapping(value = "/test/{id}", method = RequestMethod.PUT)
    public Test update(@PathVariable("id") long id, @Valid @RequestBody Test updatedTest) {
        Optional<Test> currentTest = testRepository.findById(id);

        if (currentTest.isPresent()) {
            updatedTest.setId(id);
            return testRepository.saveAndFlush(updatedTest);
        }

        throw new ResourceNotFoundException();
    }

    // Delete existing item
    @RequestMapping(value = "/test/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") long id) {
        Optional<Test> currentTest = testRepository.findById(id);

        if (currentTest.isPresent()) {
            testRepository.delete(currentTest.get());
        }
        else {
            throw new ResourceNotFoundException();
        }
    }
}
