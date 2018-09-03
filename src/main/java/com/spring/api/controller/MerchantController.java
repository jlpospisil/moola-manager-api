package com.spring.api.controller;

import com.spring.api.model.Merchant;
import com.spring.api.repository.MerchantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/merchant")
public class MerchantController {

    @Autowired
    private MerchantRepository merchantRepository;

    // List all merchants
    @GetMapping
    public List<Merchant> index() {
        return merchantRepository.findAll();
    }
}
