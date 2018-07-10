package com.spring.api.repository;

import com.spring.api.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Long> {
    @Query("select a from ApplicationUser u join u.accounts a where u.id=:userId")
    List<Account> findAllByUserId(@Param("userId") Long userId);

    @Query("select a from ApplicationUser u join u.accounts a where u.id=:userId and a.id=:accountId")
    Account findOneByUserId(@Param("userId") Long userId, @Param("accountId") Long accountId);
}