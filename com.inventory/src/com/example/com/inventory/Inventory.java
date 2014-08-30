package com.example.com.inventory;

public class Inventory {
	public int _inventory;
	public int _client;
	public int _nature;
	public String _theDate;
	public int _warehouse;
	public int _paymode;
	
	public Inventory(){}
	
	public Inventory (int inventory,int client , int nature , String theDate, int warehouse, int paymode){
		this._inventory = inventory;
		this._client    = client;
		this._nature    = nature;
		this._theDate   = theDate;
		this._warehouse = warehouse;
		this._paymode   = paymode;
	}

	public int get_inventory() {
		return _inventory;
	}

	public void set_inventory(int _inventory) {
		this._inventory = _inventory;
	}

	public int get_client() {
		return _client;
	}

	public void set_client(int _client) {
		this._client = _client;
	}

	public int get_nature() {
		return _nature;
	}

	public void set_nature(int _nature) {
		this._nature = _nature;
	}

	public String get_theDate() {
		return _theDate;
	}

	public void set_theDate(String _theDate) {
		this._theDate = _theDate;
	}

	public int get_warehouse() {
		return _warehouse;
	}

	public void set_warehouse(int _warehouse) {
		this._warehouse = _warehouse;
	}

	public int get_paymode() {
		return _paymode;
	}

	public void set_paymode(int _paymode) {
		this._paymode = _paymode;
	}
	
	
}
