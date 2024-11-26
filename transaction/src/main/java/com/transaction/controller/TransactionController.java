package com.transaction.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.transaction.entity.Transaction;
import com.transaction.service.TransactionService;

import jakarta.validation.Valid;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

	@Autowired
	private TransactionService service;
	
	@GetMapping("/test")
	public ResponseEntity<String> test(){
		return new ResponseEntity<String>("Testing successfully", HttpStatus.OK);
	}

	@PostMapping("/create")
	ResponseEntity<Transaction> createTransaction(@RequestBody Transaction t, @RequestHeader("loggedInUser") int userId) {
		t.setUserId(userId);
		Transaction transaction = service.createTransaction(t);
		return new ResponseEntity<Transaction>(transaction, HttpStatus.CREATED);
	}
	
	@GetMapping("/getAll/{userId}")
	ResponseEntity<List<Transaction>> getAllTransaction(@RequestHeader("loggedInUser") int userId) {
		List<Transaction> list = service.getAllTransaction(userId);
		return new ResponseEntity<List<Transaction>>(list, HttpStatus.OK);
	}
	
	
	@PutMapping("/update/{transactionId}")
	ResponseEntity<Transaction> updateTransaction(@RequestBody Transaction prev,@PathVariable int transactionId) {
		Transaction transaction = service.updateTransaction(prev, transactionId);
		return new ResponseEntity<Transaction>(transaction, HttpStatus.OK);
	}
	
	@DeleteMapping("/delete/{transactionId}")
	ResponseEntity<Transaction> deleteTransaction(@PathVariable @Valid int transactionId) {
		Transaction transaction = service.deleteTransaction(transactionId);
		return new ResponseEntity<Transaction>(transaction, HttpStatus.OK);
	}
	

	
	
	
	
	
	

}
