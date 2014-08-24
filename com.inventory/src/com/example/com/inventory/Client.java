package com.example.com.inventory;

public class Client {
	public int client ;
	public String name ; 
	public String address;
	public String notes;
	
	
	public Client (int client , String name , String address , String notes){
		this.client = client;
		this.name = name ;
		this.address = address;
		this.notes = notes;
		
	}

	public Client(){};
	
	public int getClient() {
		return client;
	}


	public void setClient(int client) {
		this.client = client;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	public String getNotes() {
		return notes;
	}


	public void setNotes(String notes) {
		this.notes = notes;
	}
	
	
}
