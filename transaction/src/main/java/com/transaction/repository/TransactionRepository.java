package com.transaction.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.transaction.entity.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
	@Query("SELECT t FROM Transaction t WHERE t.userId = :userId")
	List<Transaction> findByUserId(@Param("userId") int userId);

	@Query("SELECT t FROM Transaction t WHERE t.transactionId = :transactionId")
	Transaction updateTransactionById(@Param("transactionId") int transactionId);
	
	@Query("SELECT t FROM Transaction t WHERE t.userId = :userId AND t.type = 'debit'")
	List<Transaction> getDebitTransaction(@Param("userId") int userId);
	
	@Query("SELECT t FROM Transaction t WHERE t.userId = :userId AND t.type = 'credit'")
	List<Transaction> getCreditTransaction(@Param("userId") int userId);
	

}
