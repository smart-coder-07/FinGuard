package com.transaction.entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Entity
public class Budget {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int budgetId;
	
	@Min(1)
	private double amount;
	
	@Size(min=3, max=20, message = "Enter valid category")
	private String category;
	
	@Positive(message = "User ID must be a positive integer")
	private int userId;
	
	@Pattern(regexp = "^(monthly|yearly)$", message = "Period must be either 'monthly' or 'yearly'")
	private String period;
	
	public Budget() {
	}
	
	public Budget(int budgetId, double amount, String category, int userId, String period) {
		super();
		this.budgetId = budgetId;
		this.amount = amount;
		this.category = category;
		this.userId = userId;
		this.period = period;
	}
	
	public int getBudgetId() {
		return budgetId;
	}
	public void setBudgetId(int budgetId) {
		this.budgetId = budgetId;
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
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getPeriod() {
		return period;
	}
	public void setPeriod(String period) {
		this.period = period;
	}
		
	
}
