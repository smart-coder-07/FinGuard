package com.debt.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.debt.dto.DebtDto;
import com.debt.model.Debt;
import com.debt.sevice.DebtService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/debt")
public class DebtController {
	
	@Autowired
	private DebtService debtService;
	
	@GetMapping("/test")
	public ResponseEntity<String> test(){
		return new ResponseEntity<String>("testing successfully", HttpStatus.OK);
	}
	//add the debt 
		@PostMapping("/add")
	    public ResponseEntity<Debt> addDebt(@RequestBody @Valid DebtDto debt, @RequestHeader("LoggedInUser") int id) {
			debt.setUserId(id);
	        Debt createdDebt = debtService.addDebt(debt);
	        return new ResponseEntity<>(createdDebt, HttpStatus.CREATED);
	    }
		
		//calculate the emi
		@GetMapping("/calculate-emi")
		public ResponseEntity<Double> calculateEMI(@RequestParam Long debtId) {
		    // Retrieve the debt using debtId
		    Debt debt = debtService.findDebtById(debtId);
		    if (debt == null) {
		        return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Debt not found
		    }
		    // Calculate EMI
		    double emi = debtService.calculateEMI(debt);
		    // Return the calculated EMI in the response
		    return new ResponseEntity<>(emi, HttpStatus.OK);
		}



		// get total-interest by passing id
		 @GetMapping("/total-interest")
		    public ResponseEntity<Double> calculateTotalInterest(@RequestParam Long debtId) {
		        // Retrieve the debt object from the service
		        Debt debt = debtService.findDebtById(debtId);
		        // Calculate total interest
		        double totalInterest = debtService.calculateTotalInterest(debt);
		        // Return the result
		        return new ResponseEntity<>(totalInterest, HttpStatus.OK);
		    }

	//get generate schedule by passing id
		 @GetMapping("/generate-schedule")
		    public ResponseEntity<List<String>> generateRepaymentSchedule(@RequestParam Long debtId) {
		        // Retrieve the debt object from the service
		        Debt debt = debtService.findDebtById(debtId);
		        // Generate repayment schedule
		        List<String> repaymentSchedule = debtService.generateRepaymentSchedule(debt);
		        // Return the repayment schedule
		        return new ResponseEntity<>(repaymentSchedule, HttpStatus.OK);
		    }
		 
		 //get user by userid
		@GetMapping("/user/{userId}")
	    public List<Debt> getDebtsByUserId(@PathVariable int userId) {
	        return debtService.findDebtsByUserId(userId);
	    }
		
		// retrieve all debts
		@GetMapping("/debts")  
	    public List<Debt> getAllDebts() {
	        return debtService.findAll(); 
	    }
		

}
