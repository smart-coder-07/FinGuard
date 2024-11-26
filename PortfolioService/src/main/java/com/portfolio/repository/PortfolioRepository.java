package com.portfolio.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.portfolio.model.Portfolio;

import jakarta.transaction.Transactional;

@Repository
public interface PortfolioRepository extends JpaRepository<Portfolio, Integer> {
	
	List<Optional<Portfolio>> findByUserId(int userId);
	
	 @Modifying
	 @Transactional
	 @Query("UPDATE Portfolio p SET p.currentPrice = :currentPrice where p.assetName = :assetName")
	 void updateAllCurrentPrices(double currentPrice, String assetName);
	 
	 void deleteByassetName(String assetName);
	 
	
	
}
