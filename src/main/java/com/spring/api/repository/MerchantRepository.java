package com.spring.api.repository;

import com.spring.api.model.Merchant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MerchantRepository extends JpaRepository<Merchant, Long> {
    @Query("select m from Merchant m where m.name like :name")
    Merchant findOneByName(@Param("name") String name);
}