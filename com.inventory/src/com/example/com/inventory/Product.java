package com.example.com.inventory;

import android.R.string;

public class Product {
	public int _product ;
	public String _name ;
	public int _measurement;
	
	public Product (int product , String name , int measurement ){
		this._product = product;
		this._name = name; 
		this._measurement = measurement;
	}
	
	public Product (){}

	public int get_product() {
		return _product;
	}

	public void set_product(int _product) {
		this._product = _product;
	}

	public String get_name() {
		return _name;
	}

	public void set_name(String _name) {
		this._name = _name;
	}

	public int get_measurement() {
		return _measurement;
	}

	public void set_measurement(int _measurement) {
		this._measurement = _measurement;
	}
	
	
	
	
}
