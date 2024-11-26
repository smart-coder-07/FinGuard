package com.transaction.entity;

import java.sql.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

@Entity
public class Transaction {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int transactionId;

	@Min(1)
	private double amount;

	@Size(min = 3, max = 50, message = "Category cannot exceed 50 characters")
	private String category;

	private Date date;
	
	private String type = "debit";

	@Positive(message = "User ID must be a positive integer")
	private int userId;
	
	String userName;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	private String message = "Your transaction is successfull!";

	@Override
	public String toString() {
		return "Transaction [transactionId=" + transactionId + ", amount=" + amount + ", category=" + category
				+ ", date=" + date + ", type=" + type + ", userId=" + userId + ", message=" + message + "]";
	}

	public int getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(int transactionId) {
		this.transactionId = transactionId;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	public void setMessage(String message) {
		this.message = message;
	}

	public int getUserId() {
		return userId;
	}
	public String getMessage() {
		return message;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	

}
