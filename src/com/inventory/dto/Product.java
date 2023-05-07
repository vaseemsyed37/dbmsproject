package com.inventory.dto;

import java.util.List;

public class Product {
	private String prod;
	private String pname;
	private Integer price;
	private Stock stock;
	private List<Stock> stockList;
	
	public Product(String prod, String pname, Integer price) {
		super();
		this.prod = prod;
		this.pname = pname;
		this.price = price;
	}

	public String getProd() {
		return prod;
	}

	public void setProd(String prod) {
		this.prod = prod;
	}

	public String getPname() {
		return pname;
	}

	public void setPname(String pname) {
		this.pname = pname;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}
	
	public List<Stock> getStockList() {
		return stockList;
	}

	public void setStockList(List<Stock> stockList) {
		this.stockList = stockList;
	}
	
	public Stock getStock() {
		return stock;
	}

	public void setStock(Stock stock) {
		this.stock = stock;
	}

	@Override
	public String toString() {
		return "Product [prod=" + prod + ", pname=" + pname + ", price=" + price + ", stockList=" + stockList + "]";
	}
}
