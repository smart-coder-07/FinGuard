package com.budget.controller;
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
import com.budget.service.BudgetService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;

import com.budget.model.Budget;

@RestController
@RequestMapping("/budget")
public class BudgetController {
	 
	@Autowired
	public BudgetService budgetservice;
	
	
	@GetMapping("/test")
	public ResponseEntity<String> test(){
		return new ResponseEntity<String>("Testing",HttpStatus.CREATED);
	}
	
	@PostMapping("/create")
	public ResponseEntity<Budget> setBudget(@RequestBody @Valid Budget budget, @RequestHeader("loggedInUser") int id){
		budget.setUserId(id);
		Budget budgetObj= budgetservice.setBudget(budget);
		return new ResponseEntity<Budget>(budgetObj,HttpStatus.CREATED);
		
	}

//	@GetMapping("/getbybudgetid/{id}")
//	public ResponseEntity<List<Budget>> getByBudgetid(@PathVariable @Valid int id){
//		List<Budget> budget=budgetservice.getByBudgetid(id); 
//		return new ResponseEntity<List<Budget>>(budget,HttpStatus.OK);
//	}
	
	@GetMapping("/getbyuserid")
	public ResponseEntity<List<Budget>> getByuserId(@RequestHeader("loggedInUser") @Valid int uerid){
		List<Budget> budget=budgetservice.getByuserId(uerid); 
		return new ResponseEntity<List<Budget>> (budget,HttpStatus.OK);
	}
	
	@GetMapping("/getbycategory/{category}")
	public ResponseEntity<List<Budget>> getBybcategory(@PathVariable @Valid String category, @RequestHeader("loggedInUser") int userId){
		List<Budget> budget=budgetservice.getBycategory(category, userId);
		return new ResponseEntity<List<Budget>>(budget,HttpStatus.OK);
	}
	
	@GetMapping("/getcategory")
	public ResponseEntity<List<String>> getcategory(@RequestHeader("loggedInUser") int userId){
		List<String> list=budgetservice.getCategory(userId); 
		return new ResponseEntity<List<String>>(list,HttpStatus.OK);
	}
	
	@DeleteMapping("/delete/{category}")
	public ResponseEntity<List<Budget>> delete(@PathVariable @Valid String category, @RequestHeader("loggedInUser") int userId){
		//String message= budgetservice.delete(category);
		List<Budget> list=budgetservice.delete(category, userId);
		return new ResponseEntity<List<Budget>>(list,HttpStatus.OK);
		
	}
	
	@PutMapping("/update")
	public ResponseEntity<String> updateByCategory(@RequestHeader("loggedInUser") int userId, @RequestParam("category") @Valid String category, @RequestParam("amount") @Valid double amount){
		budgetservice.updateByCategory(userId, category, amount); 
		return new ResponseEntity<String>("Updated",HttpStatus.OK);
	}
	
	@GetMapping("/getSingleBudget")
	public ResponseEntity <Budget> getOneObjectByUserIdAndCategory(@RequestHeader("loggedInUser") int userId, @RequestParam("category") @Valid String category){
		Budget budget=budgetservice.getOneObjectByUserIdAndCategory(userId, category);
		return new ResponseEntity<Budget>(budget,HttpStatus.OK);
	}
 

	
//	@PutMapping("/update")
//	public ResponseEntity<List<Budget>> updateByCategory(@RequestParam("userId") @Valid int userId, @RequestParam("category") @Valid String category, @RequestParam("amount") @Valid double amount){
//		List<Budget> budget=budgetservice.updateByCategory(userId, category, amount); 
//		return new ResponseEntity<List<Budget>>(budget,HttpStatus.OK);
//	}
}
