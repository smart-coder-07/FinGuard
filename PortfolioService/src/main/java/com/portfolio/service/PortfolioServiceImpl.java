package com.portfolio.service;

import java.util.ArrayList;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.portfolio.exception.PortfolioCustomException;
import com.portfolio.model.Portfolio;
import com.portfolio.model.Transaction;
import com.portfolio.model.User;
import com.portfolio.repository.PortfolioRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class PortfolioServiceImpl implements PortfolioService {
	
	@Autowired
	private PortfolioRepository portfolioRepository;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private Transaction transaction;

	@Override
	public Portfolio add(Portfolio p) {
		String urlTransaction = "http://transaction/transaction/create";
		transaction.setAmount(p.getPurchasePrice());
		transaction.setUserId(p.getUserId());
		transaction.setCategory(p.getAssetType());
		transaction.setType("debit");
		transaction.setMessage("Your "+p.getAssetName()+" transaction is successful");
		transaction.setUserId(p.getUserId());
		System.out.println(transaction);
		HttpHeaders headers = new HttpHeaders();
		headers.set("loggedInUser", String.valueOf(p.getUserId()));
		HttpEntity<Transaction> entity = new HttpEntity<>(transaction,headers);
		ResponseEntity<Transaction> transactionEntity = restTemplate.exchange(urlTransaction, HttpMethod.POST, entity, Transaction.class);
//		restTemplate.postForObject(urlTransaction, transaction, Transaction.class);
		System.out.println(transactionEntity.getBody());
		String urlUser = "http://userservice/auth/"+p.getUserId();
		User user = null;
		try {
			user = restTemplate.getForObject(urlUser, User.class);	
		} catch (Exception e) {
			throw new PortfolioCustomException("User not found");
		}
			p.setUsername(user.getUsername());
			p.setCurrentPrice(p.getPurchasePrice());
			return portfolioRepository.save(p);
	}
	
	@Override
	public List<Portfolio> getAllInvestmentByUserId(int userId) {
		List<Optional<Portfolio>> investmentList = portfolioRepository.findByUserId(userId);
		if(investmentList.isEmpty()) {
			throw new PortfolioCustomException("User ID is not found");
		}
		List<Portfolio> investments = new ArrayList<Portfolio>();
		for(Optional<Portfolio> o: investmentList) {
			investments.add(o.get());
		}
		return investments;
	}

	@Override
	public List<Portfolio> getAllInvestmentByType(int userId, String assetType) {
		List<Optional<Portfolio>> investmentList = portfolioRepository.findByUserId(userId);
		if(investmentList.isEmpty()) {
			throw new PortfolioCustomException("User ID is not found");
		}
		List<Portfolio> investments = new ArrayList<Portfolio>();
		for(Optional<Portfolio> o: investmentList) {
			investments.add(o.get());
		}
		investments = investments.stream().filter((a)->a.getAssetType().equals(assetType)).toList();
		return investments;
	}

	@Override
	public String updateCurrentPrice(double currentPrice, String assetName) {
		portfolioRepository.updateAllCurrentPrices(currentPrice, assetName);
		return "Price updated successfully";
	}

	@Override
	public List<Portfolio> getAllInvestments() {
		List<Portfolio> allInvestments = portfolioRepository.findAll();
		if(allInvestments.isEmpty()) {
			throw new PortfolioCustomException("No investment found");
		}
		else {
			return allInvestments;
		}
	}

	@Override
	public String deleteInvestmentByAssetName(String assetName) {
		portfolioRepository.deleteByassetName(assetName);
		return assetName+" investment deleted successfully";
	}

}
