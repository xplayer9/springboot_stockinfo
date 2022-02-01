package com.stock.dto;

import lombok.Data;

@Data
public class StockDTO {
	private Long id; //pid
	private String userName; //Justin
	private String symbol; //AAPL
	private Double price;
	private Integer volume;
	private String name; //Apple Inc.
	private Double changesPercentage; //6.97776700,
	private Double change; //11.11000100
	private Double previousClose; //159.22000000,
	private int timestamp; //1643582789
	
	private String exchange;
	private String exchangeShortName;
}
