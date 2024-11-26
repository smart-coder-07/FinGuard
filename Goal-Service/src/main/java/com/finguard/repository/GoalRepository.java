package com.finguard.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.finguard.model.GoalModel;

import jakarta.transaction.Transactional;

@Repository
public interface GoalRepository extends JpaRepository<GoalModel, Integer>{

	@Query("FROM GoalModel WHERE userId= :userId AND goalName= :goalName")
	GoalModel getByGoalName(int userId, String goalName);

	@Modifying
	@Transactional
	@Query("DELETE FROM GoalModel WHERE userId = :userId AND goalName = :goalName")
	void deleteGoal(@Param("userId") int userId, @Param("goalName") String goalName);

	@Query("FROM GoalModel WHERE userId= :userId")
	List<GoalModel> getGoals(int userId);
	
//	@Query("SELECT t FROM Transaction t WHERE t.userId = :userId")
//	List<GoalModel> findByUserId(@Param("userId") int userId);

}
