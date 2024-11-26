package com.finguard.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.finguard.exception.GoalsCustomException;
import com.finguard.model.GoalModel;
import com.finguard.model.User;
import com.finguard.repository.GoalRepository;

@Service
public class GoalServiceImpl implements GoalService {

	@Autowired
	private GoalRepository goalRepository;
	
	@Autowired
	private RestTemplate restTemplate;

	@Override
	public GoalModel createGoal(GoalModel goal) {
		
		String urlUser = "http://userservice/auth/" + goal.getUserId();
	    User user = null;
	    try {
	        user = restTemplate.getForObject(urlUser, User.class);
	        if (user == null) {
	            throw new GoalsCustomException("User not found for ID: " + goal.getUserId());
	        }
	    } catch (Exception e) {
	        // Log the error message for debugging
	        System.err.println("Error occurred while fetching user: " + e.getMessage());
	       throw new GoalsCustomException("User not found for ID: " + goal.getUserId());
	    }
 
	
	    goal.setUserName(user.getUsername());
 
	    // Save the debt record
	    return goalRepository.save(goal);
	}

	@Override
	public String updateProgress(int userId, String goalName, double amountSaved) {
		GoalModel goal = goalRepository.getByGoalName(userId, goalName);
		if (goal == null) {
			throw new GoalsCustomException("Goal not Found");
		}
		if(goal.getCurrentAmount()+amountSaved > goal.getTargetAmount()) {
			throw new GoalsCustomException("Your Current Amount Should not be greater than target amount");
		}
		goal.setCurrentAmount(goal.getCurrentAmount() + amountSaved);
		goalRepository.save(goal);
		if(goal.getCurrentAmount() == goal.getTargetAmount()) {
			return "Congratulations!!! you have achieved your goal...";
		}
		
		return "Your current amount has been updated succesfully!";
	}

	@Override
	public GoalModel getByGoalName(int userId, String goalName) {
		return goalRepository.getByGoalName(userId, goalName);
	}

	@Override
	public List<GoalModel> getGoals(int userId) {
		List<GoalModel> goals = goalRepository.getGoals(userId);
		if(goals.isEmpty()) {
			throw new GoalsCustomException("Goals are not found");
		}
		return goals;
				
	}

	@Override
	public String deleteGoal(int userId, String goalName) {

		GoalModel goal = goalRepository.getByGoalName(userId, goalName);
		if (goal == null) {
			throw new GoalsCustomException("Goal not found");
		}

		goalRepository.deleteById(goal.getGoalId());
		return "Goal deleted";
	}

}
