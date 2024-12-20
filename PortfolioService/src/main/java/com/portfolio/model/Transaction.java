package com.portfolio.model;

import java.sql.Date;

import org.springframework.stereotype.Component;

import lombok.Data;
@Component
@Data
public class Transaction {
	private int transactionId;

	private double amount;

	private String category;

	private Date date;

	private String type;

	private int userId;

	private String message;


}
