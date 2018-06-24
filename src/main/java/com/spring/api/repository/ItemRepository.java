package com.spring.api.repository;

import com.spring.api.model.ApplicationUser;
import com.spring.api.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {
    @Query("select i from ApplicationUser u join u.items i where u.id=:userId")
    List<Item> findbyUserId(@Param("userId") Long userId);
}