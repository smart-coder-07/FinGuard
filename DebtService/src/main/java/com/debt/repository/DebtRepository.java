package com.debt.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.debt.model.Debt;
@Repository
public interface DebtRepository extends JpaRepository<Debt, Long>{
	
	List<Debt> findByUserId(int userId);

	//List<Debt> findAll(Debt debt);

}

