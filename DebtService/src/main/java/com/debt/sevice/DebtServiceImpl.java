package com.debt.sevice;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.debt.dto.DebtDto;
import com.debt.dto.User;
import com.debt.exception.DebtCustomException;
import com.debt.model.Debt;
import com.debt.repository.DebtRepository;

import jakarta.validation.Valid;
@Service
public class DebtServiceImpl implements DebtService{
	@Autowired
	private DebtRepository debtRepository;
	@Autowired
	private RestTemplate restTemplate;


	public Debt addDebt(DebtDto debt) {
	    System.out.println("Adding Debt for User: " + debt.getUserId());

	    Debt d = new Debt();
	    d.setDept_Type(debt.getDept_Type());
	    d.setDue_Date(debt.getDue_Date());
	    d.setId(debt.getId());
	    d.setPrinciple_Amount(debt.getPrinciple_Amount());
	    d.setRemaining_Amount(debt.getRemaining_Amount());
	    d.setDuration_In_Months(debt.getDuration_In_Months());
	    d.setInterest_Rate(debt.getInterest_Rate());

	    // Form the URL for the external service to get the user
	    String urlUser = "http://userservice/auth/" + debt.getUserId();
	    User user = null;
	    try {
	        user = restTemplate.getForObject(urlUser, User.class);
	        if (user == null) {
	            throw new DebtCustomException("User not found for ID: " + debt.getUserId());
	        }
	    } catch (Exception e) {
	        // Log the error message for debugging
	        System.err.println("Error occurred while fetching user: " + e.getMessage());
	        throw new DebtCustomException("User not found for ID: " + debt.getUserId());
	    }

	    d.setUserId(debt.getUserId());
	    d.setUsername(user.getUsername());

	    // Save the debt record
	    return debtRepository.save(d);
	}


	
	public double calculateEMI(@Valid Debt debt) {
        double monthlyInterestRate = (debt.getInterest_Rate() / 100) / 12;
        return (debt.getPrinciple_Amount() * monthlyInterestRate * Math.pow(1 + monthlyInterestRate, debt.getDuration_In_Months())) /
                (Math.pow(1 + monthlyInterestRate, debt.getDuration_In_Months()) - 1);
    }
	
	public double calculateTotalInterest( Debt debt) {
		 double emi = calculateEMI(debt);
	     return emi * debt.getDuration_In_Months() - debt.getPrinciple_Amount();
	}
	
	public List<String> generateRepaymentSchedule(Debt debt) {
		double emi = calculateEMI(debt);
        double remainingAmount = debt.getRemaining_Amount();
        double monthlyInterestRate = (debt.getInterest_Rate() / 100) / 12;
        
        List<String> repaymentSchedule = new ArrayList<>();
        for (int month = 1; month <= debt.getDuration_In_Months(); month++) {
            double interestPayment = remainingAmount * monthlyInterestRate;
            double principalPayment = emi - interestPayment;
            remainingAmount -= principalPayment;

            if (remainingAmount < 0) remainingAmount = 0;

            repaymentSchedule.add(String.format("Month: %d | Principal Payment: %.2f | Interest Payment: %.2f | Remaining Balance: %.2f",
                    month, principalPayment, interestPayment, remainingAmount));
        }
        return repaymentSchedule;
	}
	
	
	public List<Debt> findDebtsByUserId(int userId) {
	    return debtRepository.findByUserId(userId); // Assuming you add a custom query in your repository
	}


	public Debt findDebtById(Long debtId) {
	    return debtRepository.findById(debtId)
	            .orElseThrow(() -> new RuntimeException("Debt not found"));
	}

	@Override
	public List<Debt> findAll() {
	    return debtRepository.findAll(); 
	}

	
	
	

	


}
