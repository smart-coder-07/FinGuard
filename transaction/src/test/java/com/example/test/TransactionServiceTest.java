package com.example.test;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.transaction.TransactionApplication;
import com.transaction.entity.Transaction;
import com.transaction.exception.TransactionCustomException;
import com.transaction.repository.TransactionRepository;
import com.transaction.service.TransactionServiceImpl;

@SpringBootTest(classes = TransactionApplication.class)
@Transactional
public class TransactionServiceTest {

	@Autowired
	private TransactionServiceImpl transactionService;

	@Autowired
	private TransactionRepository transactionRepository;

	@Autowired
	private static Transaction sampleTransaction;

	@BeforeEach
	void setUp() {
		sampleTransaction = new Transaction();
		sampleTransaction.setUserId(1);
		sampleTransaction.setCategory("rent");
		sampleTransaction.setAmount(50);
		sampleTransaction.setDate(Date.valueOf(LocalDate.now()));
		sampleTransaction.setType("debit");
		transactionRepository.save(sampleTransaction);
	}

	@Test
	void testCreateTransaction() {
		assertNotNull(sampleTransaction);
		assertEquals(1, sampleTransaction.getUserId());
	}

	@Test
	void testGetAllTransaction() {
		List<Transaction> transactions = transactionService.getAllTransaction(1);
		assertNotNull(transactions);
		assertFalse(transactions.isEmpty());
		assertEquals("stock", transactions.get(0).getCategory());
	}

	@Test
	void testGetAllTransaction_NoTransactionsFound() {
		TransactionCustomException exception = assertThrows(TransactionCustomException.class,
				() -> transactionService.getAllTransaction(999));
		assertEquals("No transactions found by the user", exception.getMessage());
	}

	@Test
	void testUpdateTransaction() {
		Transaction updatedDetails = new Transaction();
		updatedDetails.setCategory("rent");
		updatedDetails.setType("debit");

		Transaction updatedTransaction = transactionService.updateTransaction(updatedDetails,
				sampleTransaction.getTransactionId());
		assertNotNull(updatedTransaction);
		assertEquals("rent", updatedTransaction.getCategory());
		assertEquals("debit", updatedTransaction.getType());
	}

	@Test
	void testUpdateTransaction_NoTransactionFound() {
		Transaction updatedDetails = new Transaction();
		updatedDetails.setCategory("Entertainment");

		TransactionCustomException exception = assertThrows(TransactionCustomException.class,
				() -> transactionService.updateTransaction(updatedDetails, 999));
		assertEquals("No transaction found by the id", exception.getMessage());
	}

	@Test
	void testDeleteTransaction() {
		Transaction deletedTransaction = transactionService.deleteTransaction(sampleTransaction.getTransactionId());
		assertNotNull(deletedTransaction);
		assertEquals(sampleTransaction.getTransactionId(), deletedTransaction.getTransactionId());

		List<Transaction> transactions = transactionRepository.findByUserId(4);
		assertTrue(transactions.isEmpty());
	}

	@Test
	void testDeleteTransaction_NoTransactionFound() {
		TransactionCustomException exception = assertThrows(TransactionCustomException.class,
				() -> transactionService.deleteTransaction(999));
		assertEquals("No transaction found by the id", exception.getMessage());
	}

}
