package com.portfolio.service;

import java.util.List;


import com.portfolio.model.Portfolio;

public interface PortfolioService {
	
	Portfolio add(Portfolio p);
	List<Portfolio> getAllInvestmentByUserId(int userId);
	List<Portfolio> getAllInvestmentByType(int userId, String assetType);
	String updateCurrentPrice(double currentPrice, String assetName);
	List<Portfolio> getAllInvestments();
	String deleteInvestmentByAssetName(String assetName);
	

}
