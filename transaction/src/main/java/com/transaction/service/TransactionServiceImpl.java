package com.transaction.service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.transaction.entity.Budget;
import com.transaction.entity.Transaction;
import com.transaction.entity.UserDto;
import com.transaction.exception.TransactionCustomException;
import com.transaction.repository.TransactionRepository;

@Service
public class TransactionServiceImpl implements TransactionService {

	@Autowired
	private TransactionRepository repo;

	@Autowired
	private RestTemplate restTemplate;

	@Override
	public Transaction createTransaction(Transaction transaction) {
 
		try {
 
			String getSingleBudgetUrl = "http://budgetservice/budget/getSingleBudget?category="
					+ transaction.getCategory();
			transaction.setDate(Date.valueOf(LocalDate.now()));
 
			String getUserUrl = "http://userservice/auth/" + transaction.getUserId();
			UserDto user = restTemplate.getForObject(getUserUrl, UserDto.class);
			transaction.setUserName(user.getUsername());
			HttpHeaders headers = new HttpHeaders();
			headers.set("loggedInUser", String.valueOf(transaction.getUserId()));
 
			HttpEntity<Budget> requestEntity = new HttpEntity<>(headers);
 
			ResponseEntity<Budget> budgetEntity = restTemplate.exchange(getSingleBudgetUrl, HttpMethod.GET,
					requestEntity, Budget.class);
 
			Budget budget = budgetEntity.getBody();
 
			if (budget == null) {
				throw new TransactionCustomException("Budget not found for the user name " + user.getUsername());
			}
			if (budget.getAmount() - transaction.getAmount() < 1) {
				throw new TransactionCustomException("Your transaction amount is exceeding the budget");
			}
 
			String updateUrl = "http://budgetservice/budget/update?category=" + transaction.getCategory() + "&amount="
					+ (budget.getAmount() - transaction.getAmount());
 
			restTemplate.exchange(updateUrl, HttpMethod.PUT, requestEntity, Void.class);
		} catch (Exception e) {
			throw new TransactionCustomException(e.getMessage());
		}
		transaction.setMessage("Your "+transaction.getCategory()+" transaction is successful");
 
		return repo.save(transaction);
	}

	@Override
	public List<Transaction> getAllTransaction(int userId) {
		List<Transaction> list = repo.findByUserId(userId);
		for (Transaction t : list) {
			System.out.println(t);
		}
		if (list.isEmpty()) {
			throw new TransactionCustomException("No transactions found by the user");
		}
		return list;
	}

	@Override
	public Transaction updateTransaction(Transaction prev, int transactionId) {

		Transaction tr = repo.updateTransactionById(transactionId);
		if (tr == null) {
			throw new TransactionCustomException("No transaction found by the id");
		}

		if (prev.getCategory() != null) {
			tr.setCategory(prev.getCategory());
		}
		if (prev.getType() != null) {
			tr.setType(prev.getType());
		}

		return repo.save(tr);

	}


	@Override
	public Transaction deleteTransaction(int transactionId) {
	    Transaction transaction = repo.updateTransactionById(transactionId);
	    if (transaction == null) {
	        throw new TransactionCustomException("No transaction found by the id");
	    }
	    repo.deleteById(transactionId);
	    return transaction;
	}


}
