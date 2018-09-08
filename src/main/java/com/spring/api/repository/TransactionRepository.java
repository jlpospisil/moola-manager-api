package com.spring.api.repository;

import com.spring.api.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    @Query("SELECT t FROM Transaction t JOIN FETCH t.account WHERE t.id = (:id)")
    Transaction findByIdAndFetchAccountEagerly(@Param("id") Long id);

    @Query("select t from Account a join a.transactions t where a.id=:accountId")
    List<Transaction> findAllByAccountId(@Param("accountId") Long accountId);

    @Query("select t from Account a join a.transactions t where a.id=:accountId and t.id=:transactionId")
    Transaction findOneByAccountId(@Param("accountId") Long accountId, @Param("transactionId") Long transactionId);

    @Query("select t from ApplicationUser u join u.accounts a join a.transactions t where u.id=:userId and t.id=:transactionId")
    Transaction findOneByUserIdAndTransactionId(@Param("userId") Long userId, @Param("transactionId") Long transactionId);

    @Query("select t from ApplicationUser u join u.accounts a join a.transactions t where u.id=:userId order by t.id desc")
    List<Transaction> findAllByUserId(@Param("userId") Long userId);
}