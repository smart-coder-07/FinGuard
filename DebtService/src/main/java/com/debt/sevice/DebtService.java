package com.debt.sevice;

import java.util.List;

import com.debt.dto.DebtDto;
import com.debt.model.Debt;

public interface DebtService {
	
	Debt addDebt(DebtDto debt);
	double calculateEMI(Debt debt);
	double calculateTotalInterest(Debt debt);
	List<String> generateRepaymentSchedule(Debt debt);
	Debt findDebtById(Long debtId);
	List<Debt> findDebtsByUserId(int userId);
	List<Debt> findAll();

}
