package com.example.com.inventory;

public class Warehouse {

		public int _warehouse;
		public String _name;
		public String _address;
		
		public Warehouse(){}
		
		//constructor
		public Warehouse(int warehouse , String name, String address){
			
			this._warehouse = warehouse;
			this._name       = name ;
			this._address    = address;
		}
		
		
		public int get_warehouse() {
			return _warehouse;
		}

		public void set_warehouse(int _warehouse) {
			this._warehouse = _warehouse;
		}

		public String get_name() {
			return _name;
		}

		public void set_name(String _name) {
			this._name = _name;
		}

		public String get_address() {
			return _address;
		}

		public void set_address(String _address) {
			this._address = _address;
		}
		
}
