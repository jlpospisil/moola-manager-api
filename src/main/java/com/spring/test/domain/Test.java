package com.spring.test.domain;

import java.io.Serializable;
import javax.persistence.*;

@Entity
//@Table(name = "different talbe name than test")
public class Test implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column
    private String description;

    // ... additional members, often include @OneToMany mappings

    protected Test() {
        // no-args constructor required by JPA spec
        // this one is protected since it shouldn't be used directly
    }

    public Test(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }
}