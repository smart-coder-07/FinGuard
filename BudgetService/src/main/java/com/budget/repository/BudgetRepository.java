package com.budget.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.budget.model.Budget;

import jakarta.transaction.Transactional;
@Repository
public interface BudgetRepository extends JpaRepository<Budget, Integer>{

	@Query("FROM Budget where userId = :Id")
	List<Budget> getByuserId(@Param("Id") int userId);

	@Query("SELECT b FROM Budget b where b.budgetId = :budgetId")
	List<Budget> getByBudgetid(@Param("budgetId") int budgetId);

	@Query("FROM Budget where category = :category AND userId = :userId")
	List<Budget> getBycategory(@Param("category") String category, @Param("userId") int userId);

	//@Query("UPDATE Portfolio p SET p.currentPrice = :currentPrice where p.assetName = :assetName")
	@Modifying
	@Transactional
	@Query("FROM Budget WHERE userId = :userId AND category = :category")
	List<Budget> getByIdCategory( int userId,  String category);

	@Query("SELECT b.category FROM Budget b WHERE b.userId = :userId")
	List<String> getCategory(int userId);

	@Modifying
	@Transactional
	@Query("update Budget set amount= :amount WHERE userId= :userId and category= :category")
	void updateByCategory(int userId, String category, double amount);

	@Modifying
	@Query("DELETE FROM Budget b WHERE b.category = :category")
	void delete(@Param("category") String category);
	
	@Query("FROM Budget b where b.userId = :userId AND b.category = :category")
	Budget getOneObjectByUserIdAndCategory(@Param("userId") int userId, @Param("category") String category);

}
