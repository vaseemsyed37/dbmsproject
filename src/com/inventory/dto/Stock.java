package com.inventory.dto;

public class Stock {
	private String prod;
	private String dep;
	private Integer quantity;

	public Stock(String prod, String dep, Integer quantity) {
		super();
		this.prod = prod;
		this.dep = dep;
		this.quantity = quantity;
	}

	public String getProd() {
		return prod;
	}

	public void setProd(String prod) {
		this.prod = prod;
	}

	public String getDep() {
		return dep;
	}

	public void setDep(String dep) {
		this.dep = dep;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	@Override
	public String toString() {
		return "Stock [prod=" + prod + ", dep=" + dep + ", quantity=" + quantity + "]";
	}

}
