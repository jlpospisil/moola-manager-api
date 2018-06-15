package com.spring.test;

import com.spring.test.domain.Test;
import com.spring.test.domain.TestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class TestController {

    @Autowired
    private TestRepository testRepository;

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public List index() {

        return testRepository.findAll();
    }

    @RequestMapping(value = "/test", method = RequestMethod.POST)
    public Test save(@Valid @RequestBody Test test) {

        return test;
    }
}
