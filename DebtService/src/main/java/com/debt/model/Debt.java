package com.debt.model;

import java.sql.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;


@Entity
public class Debt {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private String dept_Type;
	private double principle_Amount;
	private double interest_Rate;
	private int duration_In_Months;
	private double remaining_Amount;
	private Integer userId;
	private String username;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}

	private String due_Date;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getDept_Type() {
		return dept_Type;
	}
	public void setDept_Type(String dept_Type) {
		this.dept_Type = dept_Type;
	}
	public double getPrinciple_Amount() {
		return principle_Amount;
	}
	public void setPrinciple_Amount(double principle_Amount) {
		this.principle_Amount = principle_Amount;
	}
	public double getInterest_Rate() {
		return interest_Rate;
	}
	public void setInterest_Rate(double interest_Rate) {
		this.interest_Rate = interest_Rate;
	}
	public int getDuration_In_Months() {
		return duration_In_Months;
	}
	public void setDuration_In_Months(int duration_In_Months) {
		this.duration_In_Months = duration_In_Months;
	}
	public double getRemaining_Amount() {
		return remaining_Amount;
	}
	public void setRemaining_Amount(double remaining_Amount) {
		this.remaining_Amount = remaining_Amount;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer i) {
		this.userId = i;
	}
	public String getDue_Date() {
		return due_Date;
	}
	public void setDue_Date(String due_Date) {
		this.due_Date = due_Date;
	}
	public Debt(long id, String dept_Type, double principle_Amount, double interest_Rate, int duration_In_Months,
			double remaining_Amount, Integer userId, String due_Date) {
		super();
		this.id = id;
		this.dept_Type = dept_Type;
		this.principle_Amount = principle_Amount;
		this.interest_Rate = interest_Rate;
		this.duration_In_Months = duration_In_Months;
		this.remaining_Amount = remaining_Amount;
		this.userId = userId;
		this.due_Date = due_Date;
	}
	
	public Debt() {
		// TODO Auto-generated constructor stub
	}
	
	
}
