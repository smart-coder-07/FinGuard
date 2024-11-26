package com.budget;
 
import com.budget.model.Budget;
import com.budget.repository.BudgetRepository;
import com.budget.service.BudgetService;
import com.budget.service.BudgetServiceImpl;
 
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
 
import java.util.List;
 
import static org.junit.jupiter.api.Assertions.*;
 
@SpringBootTest
@Transactional
public class BudgetServiceTest {
		@Autowired
		private BudgetService budgetServiceImpl;
		@Autowired
		private BudgetRepository budgetRepository;
		@Autowired
		private static Budget sampleBudget;
		@BeforeEach
		void setUp() {
			sampleBudget = new Budget();
			sampleBudget.setBudgetId(1);
			sampleBudget.setCategory("rent");
			sampleBudget.setAmount(50);
			sampleBudget.setPeriod("monthly");
			sampleBudget.setUserId(1);
			sampleBudget.setUserName("Rushad");
			budgetRepository.save(sampleBudget);
		}
		@Test
		void testCreateBudget() {
			var demo = new Budget();
			demo = new Budget();
			demo.setBudgetId(1);
			demo.setCategory("rent");
			demo.setAmount(50);
			demo.setPeriod("monthly");
			demo.setUserId(1);
			demo.setUserName("Rushad");

 
			Budget tr = budgetRepository.save(demo);
			assertEquals(1, tr.getUserId());
			assertNotNull(tr);
		}

}