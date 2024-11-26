package com.finguard.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.finguard.model.GoalModel;
import com.finguard.service.GoalServiceImpl;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/goals")
public class GoalController {
	@Autowired
	private GoalServiceImpl service;

	@GetMapping("/test")
	public ResponseEntity<String> test() {
		return new ResponseEntity<String>("Testing successfully", HttpStatus.OK);
	}

	@PostMapping("/create")
	public ResponseEntity<GoalModel> createGoal(@RequestBody @Valid GoalModel goal, @RequestHeader("loggedInUser") int id) {
		goal.setUserId(id);
		GoalModel goal1 = service.createGoal(goal);

		return new ResponseEntity<GoalModel>(goal1, HttpStatus.CREATED);
	}

	@DeleteMapping("/delete")
	public ResponseEntity<String> deleteGoal(@RequestParam int userId, @RequestParam String goalName) {

		String goal21 = service.deleteGoal(userId, goalName);
		return new ResponseEntity<String>(goal21, HttpStatus.OK);

	}

	@PutMapping("/updateProgress")
	@Valid
	public ResponseEntity<String> updateProgress(@RequestHeader("loggedInUser") int userId, @RequestParam String goalName,
			@RequestParam double amountSaved) {

//		GoalModel goal223 = service.updateProgress(userId, goalName, amountSaved);
//		if (goal223 == null) {
//			throw new GoalsCustomException("Goal not Found");
//		}
		return new ResponseEntity<String>(service.updateProgress(userId, goalName, amountSaved), HttpStatus.OK);

	}

	@GetMapping("/getGoals")
	@Valid
	public ResponseEntity<List<GoalModel>> getGoals(@RequestHeader("loggedInUser") int userId) {

		List<GoalModel> goals = service.getGoals(userId);
		return new ResponseEntity<List<GoalModel>>(goals, HttpStatus.OK);

	}

	@GetMapping("/getGoal")
	public ResponseEntity<GoalModel> getGoalByName(@RequestHeader("loggedInUser") int userId, @RequestParam String goalName) {

		GoalModel goal = service.getByGoalName(userId, goalName);
		return new ResponseEntity<GoalModel>(goal, HttpStatus.OK);

	}
}

