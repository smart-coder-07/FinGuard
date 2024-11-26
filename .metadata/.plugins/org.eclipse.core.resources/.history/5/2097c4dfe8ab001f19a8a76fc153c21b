package com.finguard.model;


import java.sql.Date;

import org.springframework.aot.generate.GeneratedTypeReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

@Entity
public class GoalModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int goalId;
	private int userId;
	@Size(min = 3, max = 50, message = " Goal Name cannnot exceed 50 characters")
	private String goalName;
	@Min(2)
	private double targetAmount;
	@Min(value = 1, message = "currentAmount must be greater than or equal to 1")
	private double currentAmount;
	@FutureOrPresent(message = "Date must be in the future")
	private Date targetDate;
	String userName;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getGoalId() {
		return goalId;
	}

	public void setGoalId(int goalId) {
		this.goalId = goalId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getGoalName() {
		return goalName;
	}

	public void setGoalName(String goalName) {
		this.goalName = goalName;
	}

	public double getTargetAmount() {
		return targetAmount;
	}

	public void setTargetAmount(double targetAmount) {
		this.targetAmount = targetAmount;
	}

	public double getCurrentAmount() {
		return currentAmount;
	}

	public void setCurrentAmount(double currentAmount) {
		this.currentAmount = currentAmount;
	}

	public Date getTargetDate() {
		return targetDate;
	}

	public void setTargetDate(Date targetDate) {
		this.targetDate = targetDate;
	}

}
