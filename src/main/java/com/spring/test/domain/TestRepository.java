package com.spring.test.domain;

import org.springframework.data.domain.*;
import org.springframework.data.repository.*;

import java.util.List;

public interface TestRepository extends Repository<Test, Long> {

    List<Test> findAll();

    Test findById(Long id);

    Test findByName(String name);

}