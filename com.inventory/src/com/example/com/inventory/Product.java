package com.example.com.inventory;

import android.R.string;

public class Product {
	public int _product ;
	public String _name ;
	public int _measurement;
	public int _starting_inv;
	public float _price;
	
	public Product (int product , String name , int measurement, float price  ,int starting_inv){
		this._product = product;
		this._name = name; 
		this._measurement = measurement;
		this._price = price ;
		this._starting_inv = starting_inv ;
	
	}
	
	
	public int get_starting_inv() {
		return _starting_inv;
	}

	public void set_starting_inv(int _starting_inv) {
		this._starting_inv = _starting_inv;
	}

	@Override
    public String toString() {
        return this.get_product()+"-"+this.get_name(); // You can add anything else like maybe getDrinkType()
    }
	
	public float get_price() {
		return _price;
	}

	public void set_price(float _price) {
		this._price = _price;
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
