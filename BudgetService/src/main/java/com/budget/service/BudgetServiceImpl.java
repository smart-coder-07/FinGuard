package com.budget.service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.budget.exception.BudgetCustomException;
import com.budget.model.Budget;
import com.budget.model.User;
import com.budget.repository.BudgetRepository;

import jakarta.transaction.Transactional;


@Service
@Transactional
public class BudgetServiceImpl implements BudgetService{
	
	@Autowired
	private BudgetRepository budgetrepo;
	
	static double remaningincome=20000;
	
	@Autowired
	private RestTemplate restTemplete;
	
	@Override
	public int setIncome(int income) {
		// TODO Auto-generated method stub
		
		return 0;
	}

	@Override
	public Budget setBudget(Budget budget) {
		// TODO Auto-generated method stub
		String getUser = "http://userservice/auth/"+budget.getUserId();
		User user= restTemplete.getForObject(getUser, User.class);
		budget.setUserName(user.getUsername());
		
		List<String> budgetlist= getCategory(budget.getUserId());
		
		
		if(budgetlist.contains(budget.getCategory())) {
			throw new BudgetCustomException("Category alredy exist, you can update.");
		}else {
			return budgetrepo.save(budget);
		}	
	}

	@Override
	public List<Budget> getByuserId(int userId) {
		// TODO Auto-generated method stub
		return budgetrepo.getByuserId(userId);
	}

	@Override
	public List<Budget> getByBudgetid(int id) {
		// TODO Auto-generated method stub
		return budgetrepo.getByBudgetid(id);
	}

	@Override
	public List<Budget> getBycategory(String category, int userId) {
		// TODO Auto-generated method stub
		return budgetrepo.getBycategory(category, userId);
	}

	@Override
	public List<Budget> delete(String category, int userId) {
		// TODO Auto-generated method stub
		budgetrepo.delete(category);
		return budgetrepo.getByuserId(userId);
	}

	@Override
	public void updateByCategory(int userId, String category, double amount) {
		
		budgetrepo.updateByCategory(userId,category,amount);
	}

	@Override
	public List getCategory( int userId) {
		// TODO Auto-generated method stub
		return budgetrepo.getCategory(userId);
	}

	@Override
	public Budget getOneObjectByUserIdAndCategory(int userId, String category) {
		Budget obj = budgetrepo.getOneObjectByUserIdAndCategory(userId, category);
		
		return obj;
	}


}
