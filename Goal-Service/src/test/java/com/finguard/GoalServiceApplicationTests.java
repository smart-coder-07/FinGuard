package com.finguard;

import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.finguard.exception.GoalsCustomException;
import com.finguard.model.GoalModel;
import com.finguard.repository.GoalRepository;
import com.finguard.service.GoalService;

import jakarta.transaction.Transactional;



@SpringBootTest
@Transactional
class GoalServiceApplicationTests {


	    @Autowired
	    private GoalService goalService;  // Autowire the actual service class

	    @Autowired
	    private GoalRepository goalRepository;  // Autowire the repository to interact with DB

	    private GoalModel goal1;
	    private GoalModel goal2;

	    @BeforeEach
	    public void setUp() {
	        // Clear the repository before each test to ensure a clean state
	        goalRepository.deleteAll();  // Ensures no previous data interferes with tests

	        // Set up goal data
	        goal1 = new GoalModel();
	        goal1.setUserId(1);
	        goal1.setGoalName("Save for Vacation");
	        goal1.setCurrentAmount(2000.0);
	        goal1.setTargetAmount(5000.0);

	        goal2 = new GoalModel();
	        goal2.setUserId(1);
	        goal2.setGoalName("Save for Car");
	        goal2.setCurrentAmount(1500.0);
	        goal2.setTargetAmount(10000.0);

	        // Save the goals
	        goalRepository.save(goal1);
	        goalRepository.save(goal2);
	    }

	 
	    @Test
	    void testUpdateProgress() {
	        // Test valid progress update
	        double amountSaved = 1000.0;
	        String message = goalService.updateProgress(1, "Save for Vacation", amountSaved);
	        assertEquals("Your current amount has been updated succesfully!", message);

	        // Fetch the updated goal
	        GoalModel updatedGoal = goalRepository.getByGoalName(1, "Save for Vacation");
	        assertEquals(3000.0, updatedGoal.getCurrentAmount());

	        // Test case where progress exceeds target
	        double excessiveAmount = 3000.0;
	        Exception exception = assertThrows(GoalsCustomException.class, () -> {
	            goalService.updateProgress(1, "Save for Vacation", excessiveAmount);
	        });
	        assertEquals("Your Current Amount Should not be greater than target amount", exception.getMessage());
	    }

	    @Test
	    void testGetByGoalName() {
	        GoalModel goal = goalService.getByGoalName(1, "Save for Car");
	        assertNotNull(goal);
	        assertEquals("Save for Car", goal.getGoalName());
	    }

	    @Test
	    void testGetGoals() {
	        List<GoalModel> goals = goalService.getGoals(1);
	        assertNotNull(goals);
	        assertEquals(2, goals.size());  // We should have two goals for user with id 1
	    }

	    @Test
	    void testDeleteGoal() {
	        // Delete a goal
	        String message = goalService.deleteGoal(1, "Save for Car");
	        assertEquals("Goal deleted", message);

	        // Verify goal is deleted
	        GoalModel deletedGoal = goalRepository.getByGoalName(1, "Save for Car");
	        assertNull(deletedGoal);
	    }

	    @Test
	    void testDeleteGoal_GoalNotFound() {
	        // Try deleting a non-existing goal
	        Exception exception = assertThrows(GoalsCustomException.class, () -> {
	            goalService.deleteGoal(1, "Non-existing Goal");
	        });
	        assertEquals("Goal not found", exception.getMessage());
	    }

	    @Test
	    void testGetGoals_GoalsNotFound() {
	        // Delete all goals to simulate no goals for the user
	        goalRepository.deleteAll();

	        // Try getting goals when none exist
	        Exception exception = assertThrows(GoalsCustomException.class, () -> {
	            goalService.getGoals(1);
	        });
	        assertEquals("Goals are not found", exception.getMessage());
	    }
	}



