package com.budget.service;

import java.util.List;

import com.budget.model.Budget;

import jakarta.validation.Valid;

public interface BudgetService {

	public int setIncome(int income);

	public Budget setBudget(Budget budget);

	public List<Budget> getByuserId(int userId);

	public List<Budget> getByBudgetid(int id);

	public List<Budget> getBycategory(String category, int userId);

	public List<Budget> delete(String category, int userId);

	public void updateByCategory(int userId, String category, double amount);

	public List<String> getCategory(@Valid int userId);

	public Budget getOneObjectByUserIdAndCategory(int userId, String category);

}
