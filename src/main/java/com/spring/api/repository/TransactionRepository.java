package com.spring.api.repository;

import com.spring.api.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    @Query("select t from Account a join a.transactions t where a.id=:accountId")
    List<Transaction> findAllByAccountId(@Param("accountId") Long accountId);

    @Query("select t from Account a join a.transactions t where a.id=:accountId and t.id=:transactionId")
    Transaction findOneByAccountId(@Param("accountId") Long accountId, @Param("transactionId") Long transactionId);
}