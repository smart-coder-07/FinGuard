package com.portfolio.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.portfolio.model.Portfolio;
import com.portfolio.service.PortfolioService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/portfolio")
public class PortfolioController {
	
	@Autowired
	private PortfolioService portfolioService;
	
	//Testing the service
	
	@GetMapping("/test")
	public ResponseEntity<String> test(){
		return new ResponseEntity<String>("portfolio testing successfully", HttpStatus.OK);
	}
	
	//Adding the Portfolio
	
	@PostMapping("/add")
	public ResponseEntity<Portfolio> addInvestment(@RequestBody @Valid Portfolio p, @RequestHeader("loggedInUser") int id){
		p.setUserId(id);
		return new ResponseEntity<Portfolio>(portfolioService.add(p),HttpStatus.CREATED);
	}
	
	//Get user investment by their id
	
	@GetMapping("/getInvestment/{id}")
	public ResponseEntity<List<Portfolio>> getAllInvestment(@PathVariable @Valid int id){
		return new ResponseEntity<List<Portfolio>>(portfolioService.getAllInvestmentByUserId(id), HttpStatus.ACCEPTED);
	}
	
	//get user investment by their type
	
	@GetMapping("/getInvestmentByType")
	public ResponseEntity<List<Portfolio>> getAllInvestmentByType(@RequestParam int id, @RequestParam String type){
		return new ResponseEntity<List<Portfolio>>(portfolioService.getAllInvestmentByType(id, type), HttpStatus.ACCEPTED);
	}
	
	//update any stock price
	
	@PutMapping("/update")
	public ResponseEntity<String> updateCurrentTransaction(@RequestParam double currentPrice, @RequestParam String assetName){
		return new ResponseEntity<String>(portfolioService.updateCurrentPrice(currentPrice, assetName),HttpStatus.ACCEPTED);
	}
	
	//get all Investments
	@GetMapping("/all")
	public ResponseEntity<List<Portfolio>> getAllInvestments(){
		return new ResponseEntity<List<Portfolio>>(portfolioService.getAllInvestments(),HttpStatus.ACCEPTED);
	}
	
	//delete Investment by their name
	@DeleteMapping("/delete/{name}")
	public ResponseEntity<String> deleteInvestmentByName(@PathVariable("name") String assetName){
		return new ResponseEntity<String>(portfolioService.deleteInvestmentByAssetName(assetName),HttpStatus.ACCEPTED);
	}
	

}
