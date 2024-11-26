package com.transaction.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.transaction.entity.Transaction;

@Service
public interface TransactionService {
	
	Transaction createTransaction(Transaction transaction);
	List<Transaction> getAllTransaction(int userId);
	Transaction updateTransaction(Transaction tr, int userId);
	Transaction deleteTransaction(int transactionId);


}
