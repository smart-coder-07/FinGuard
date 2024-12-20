package com.debt.dto;

import java.sql.Date;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class DebtDto {
	
	private long id;
	
	@NotBlank(message = "Dept Type cannot be null")
	@Size(min = 3, max = 20, message = "Dept type is between 3 to 20 character")
	@Pattern(regexp = "^(Home-Loan|Car-Loan|Education-Loan)$",message = "please enter valid loan type")
	private String dept_Type;
	
	@NotNull(message = "Principle amount cannot be null")
	@Min(value = 0, message = "Principal amount must be greater than zero")
	private double principle_Amount;
	
	@Min(value = 0, message = "Interest rate must be aleast 0%")
	@NotNull(message = "Interest rate cannot be null")
	private double interest_Rate;
	
	@Min(value = 1, message = "Duration must be aleast 1")
	@NotNull(message = "Duration cannot be null")
	private int duration_In_Months;
	
	@Min(value = 0, message = "Remaining amount must be greater than zero")
	@NotNull(message = "Remaining amount cannot be null")
	private double remaining_Amount;
	
	@NotNull(message = "User ID cannot be null")
	private int userId;
	
//    @FutureOrPresent(message = "Due date must be today or in the future")
	@NotNull(message = "Due Date cannot be null")
	private String due_Date;
    
    private String username;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

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

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getDue_Date() {
		return due_Date;
	}

	public void setDue_Date(String due_Date) {
		this.due_Date = due_Date;
	}

	public DebtDto(long id,
			@NotBlank(message = "Dept Type cannot be null") @Size(min = 3, max = 20, message = "Dept type is between 3 to 20 character") String dept_Type,
			@NotNull(message = "Principle amount cannot be null") @Min(value = 0, message = "Principal amount must be greater than zero") double principle_Amount,
			@Min(value = 0, message = "Interest rate must be aleast 0%") double interest_Rate,
			@Min(value = 1, message = "Duration must be aleast 1") @NotNull(message = "Duration cannot be null") int duration_In_Months,
			@Min(value = 0, message = "Remaining amount must be greater than zero") @NotNull(message = "Remaining amount cannot be null") double remaining_Amount,
			@NotNull(message = "User ID cannot be null") int userId,
			@FutureOrPresent(message = "Due date must be today or in the future") @NotNull(message = "Due Date cannot be null") String due_Date) {
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
    
    public DebtDto() {
		// TODO Auto-generated constructor stub
	}
    
    @AssertTrue(message = "Remaining amount cannot be greater than the principal amount")
    public boolean isRemainingAmountValid() {
        return remaining_Amount <= principle_Amount;
    }



}
