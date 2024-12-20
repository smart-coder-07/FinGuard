package com.transaction.entity;

import java.sql.Date;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class TransactionMessage {

	private int transactionId;

	private double amount;

	private String category;

	private Date date;

	private String type;

	private int userId;

	private String message;

	public TransactionMessage(int transactionId, double amount, String category, Date date, String type, int userId,
			String message) {
		super();
		this.transactionId = transactionId;
		this.amount = amount;
		this.category = category;
		this.date = date;
		this.type = type;
		this.userId = userId;
		this.message = message;
	}

}
