package com.portfolio.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Entity
@Data
public class Portfolio {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int investmentId;
	private int userId;
	private String username;
	@Pattern(regexp = "^(stock|mutual fund)$", message = "Asset type must be stock or mutual fund")
	private String assetType;
	@Pattern(regexp = "^[A-Za-z]{3,10}$", message = "Please enter valid asset name")
	private String assetName;
	@Min(1)
	private long quantity;
	@Min(150)
	private double purchasePrice;
	private double currentPrice;

}
