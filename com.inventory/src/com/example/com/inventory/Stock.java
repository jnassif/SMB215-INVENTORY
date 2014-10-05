package com.example.com.inventory;

public class Stock {

	public int qty ;
	public int nature;
	
	public Stock(int qty  , int nature){
		this.qty = qty;
		this.nature = nature;
	}
	
	public Stock(){
		this.qty = qty;
		this.nature = nature;
	}
	
	public int getQty() {
		return qty;
	}

	public void setQty(int qty) {
		this.qty = qty;
	}

	public int getNature() {
		return nature;
	}

	public void setNature(int nature) {
		this.nature = nature;
	}
}

