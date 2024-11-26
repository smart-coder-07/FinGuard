package com.finguard.service;

import java.util.List;

import com.finguard.model.GoalModel;


public interface GoalService {

	GoalModel createGoal(GoalModel obj);

	String updateProgress(int userId,String goalName, double amountSaved);

	List<GoalModel> getGoals(int userID);

	String deleteGoal(int userId,String goalName);

	GoalModel getByGoalName(int userId, String goalName);

}
