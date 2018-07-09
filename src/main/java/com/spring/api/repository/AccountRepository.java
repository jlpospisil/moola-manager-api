package com.spring.api.repository;

import com.spring.api.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Long> {
    @Query("select i from ApplicationUser u join u.accounts i where u.id=:userId")
    List<Account> findbyUserId(@Param("userId") Long userId);
}