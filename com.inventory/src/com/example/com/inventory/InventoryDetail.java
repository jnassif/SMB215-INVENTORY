package com.example.com.inventory;

class InventoryDetail {
	public int inventory;
	public int product ;
	public int quantity;
	public Float amount;

	public InventoryDetail(){}
	
	public InventoryDetail (int inventory ,int product , int quantity,Float amount ){
		this.inventory = inventory;
		this.product   = product;
		this.quantity  = quantity;
		this.amount    = amount;
	}

	public int getInventory() {
		return inventory;
	}

	public void setInventory(int inventory) {
		this.inventory = inventory;
	}

	public int getProduct() {
		return product;
	}

	public void setProduct(int product) {
		this.product = product;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Float getAmount() {
		return amount;
	}

	public void setAmount(Float amount) {
		this.amount = amount;
	}

	


	

}
