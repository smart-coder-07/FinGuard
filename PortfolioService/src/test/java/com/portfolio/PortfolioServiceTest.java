package com.portfolio;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.portfolio.exception.PortfolioCustomException;
import com.portfolio.model.Portfolio;
import com.portfolio.model.Transaction;
import com.portfolio.model.User;
import com.portfolio.repository.PortfolioRepository;
import com.portfolio.service.PortfolioService;
import com.portfolio.service.PortfolioServiceImpl;

import jakarta.transaction.Transactional;

@SpringBootTest(classes = PortfolioServiceApplication.class)
public class PortfolioServiceTest {
	
	
	@Autowired
	private PortfolioRepository portfolioRepo;
	
	@Autowired
	private PortfolioServiceImpl portfolioService;

    private Portfolio testPortfolio;
    
    @BeforeEach
    public void setData() {
    	testPortfolio = new Portfolio();
    	testPortfolio.setAssetName("HDFC");
    	testPortfolio.setAssetType("stock");
    	testPortfolio.setCurrentPrice(testPortfolio.getPurchasePrice());
    	testPortfolio.setInvestmentId(1);
    	testPortfolio.setPurchasePrice(testPortfolio.getPurchasePrice());
    	testPortfolio.setQuantity(10);
    	testPortfolio.setUserId(1);
    	testPortfolio.setUsername("atul");    	
    }
    
    @Test
    @Transactional
    public void testAdd() {
    	 Portfolio p = portfolioRepo.save(testPortfolio);
         assertNotNull(p);
    }
    
    @Test
    public void testGetAllInvestmentByUserId_UserNotFound() {
        
        int userId = 50;  
        assertThrows(PortfolioCustomException.class, () -> portfolioService.getAllInvestmentByUserId(userId));
    }
    
    @Test
    public void testGetAllInvestmentByUserId() {
    	List<Portfolio>  list = portfolioService.getAllInvestmentByUserId(testPortfolio.getUserId());
    	assertNotNull(list);
    	
    }
    
    @Test
    public void testGetAllInvestmentByType() {
    	List<Portfolio> result = portfolioService.getAllInvestmentByType(1, "stock");

        // Assertions
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("stock", result.get(0).getAssetType());
    }
    
    @Test
    public void testUpdateCurrentPrice() {
    	String result = portfolioService.updateCurrentPrice(1200.00, "HDFC");
    	Portfolio unchangedPortfolio = portfolioRepo.findById(testPortfolio.getUserId()).orElseThrow();
        assertEquals("Price updated successfully", result); 
        assertEquals(unchangedPortfolio.getPurchasePrice(), unchangedPortfolio.getCurrentPrice(), 0.01);
    }
    
    @Test
    public void testGetAllInvestment() {
    	List<Portfolio> allInvestments = portfolioService.getAllInvestments();
    	assertNotNull(allInvestments);
    }
    
    @Test
    public void testDeleteInvestmentByAssetName() {
    	String s = portfolioService.deleteInvestmentByAssetName("HDFC");
    	assertEquals("HDFC investment deleted successfully", s);
    }
    
}
