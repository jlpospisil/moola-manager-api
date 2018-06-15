package com.spring.test;

import com.spring.test.domain.Test;
import com.spring.test.domain.TestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TestController {

    @Autowired
    private TestRepository testRepository;

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public List index() {

        List<Test> tests = testRepository.findAll();

        return tests;
    }
}
