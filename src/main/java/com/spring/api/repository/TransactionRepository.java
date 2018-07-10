package com.spring.api.repository;

import com.spring.api.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    @Query("select t from ApplicationUser u join u.transactions t where u.id=:userId")
    List<Transaction> findAllByUserId(@Param("userId") Long userId);

    @Query("select t from ApplicationUser u join u.transactions t where u.id=:userId and t.id=:transactionId")
    Transaction findOneByUserId(@Param("userId") Long userId, @Param("transactionId") Long transactionId);

    @Query("select t from Account a join a.transactions t where a.id=:accountId")
    List<Transaction> findAllByAccountId(@Param("accountId") Long accountId);
}