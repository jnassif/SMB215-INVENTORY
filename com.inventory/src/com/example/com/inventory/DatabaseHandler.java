package com.example.com.inventory;


import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHandler extends SQLiteOpenHelper {
	
	// All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "inventory";

    // Contacts, products , client table name
    private static final String TABLE_WAREHOUSE = "warehouse";
    private static final String TABLE_PRODUCTS = "products";
    private static final String TABLE_CLIENTS = "clients";
    
    // Contacts Table Columns names
    private static final String KEY_ID = "warehouse";
    private static final String KEY_NAME = "name";
    private static final String KEY_ADDRESS = "address";
    private final ArrayList<Warehouse> warehouse_list = new ArrayList<Warehouse>();
    
    //products Table Columns names
    private static final String KEY_PROD_ID = "product";
    private static final String KEY_PROD_NAME = "name";
    private static final String KEY_PROD_MEASUREMENT = "measurement";
    private final ArrayList<Product> product_list = new ArrayList<Product>();

    //Clients Table Columns names
    private static final String KEY_CLIENT_ID = "client";
    private static final String KEY_CLIENT_NAME = "name";
    private static final String KEY_CLIENT_ADDRESS = "address";
    private static final String KEY_CLIENT_NOTE = "note";
    private final ArrayList<Client> client_list = new ArrayList<Client>();
    
    public DatabaseHandler(Context context) {
    	super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    
    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
    	//create warehouse table
		String CREATE_WAREHOUSE_TABLE = "CREATE TABLE " + TABLE_WAREHOUSE + "("
			+ KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
			+ KEY_ADDRESS + " TEXT" + ")";
		db.execSQL(CREATE_WAREHOUSE_TABLE);
		
		//create prodcuts table
		String CREATE_PRODUCT_TABLE = "CREATE TABLE " + TABLE_PRODUCTS + "("
		+ KEY_PROD_ID + " INTEGER PRIMARY KEY," + KEY_PROD_NAME + " TEXT,"
		+ KEY_PROD_MEASUREMENT + " INTEGER " + ")";
		db.execSQL(CREATE_PRODUCT_TABLE);
	
		//create client table
		String CREATE_CLIENT_TABLE = "CREATE TABLE " + TABLE_CLIENTS + "("
		+ KEY_CLIENT_ID + " INTEGER PRIMARY KEY," + KEY_CLIENT_NAME + " TEXT,"
		+ KEY_CLIENT_ADDRESS + " TEXT," + KEY_CLIENT_NOTE  + " TEXT " +  ")";
		db.execSQL(CREATE_CLIENT_TABLE);
	
    }

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_WAREHOUSE);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTS);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_CLIENTS);
		// Create tables again
		onCreate(db);
	}

	
	
	// Adding new warehouse
	public void Add_warehouse(Warehouse warehouse) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(KEY_NAME, warehouse.get_name()); // warehouse Name
		values.put(KEY_ADDRESS, warehouse.get_address()); // warehouse address Phone
		
		// Inserting Row
		db.insert(TABLE_WAREHOUSE, null, values);
		db.close(); // Closing database connection
	}
	
	// Adding new product
	public void Add_product(Product product) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(KEY_PROD_NAME, product.get_name()); // warehouse Name
		values.put(KEY_PROD_MEASUREMENT, product.get_measurement()); // warehouse address Phone
		
		// Inserting Row
		db.insert(TABLE_PRODUCTS, null, values);
		db.close(); // Closing database connection
	}
	
	// Adding new Client
	public void Add_client(Client client) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(KEY_CLIENT_NAME, client.getName()); // warehouse Name
		values.put(KEY_CLIENT_ADDRESS, client.getAddress()); // warehouse address Phone
		values.put(KEY_CLIENT_NOTE, client.getNotes()); // warehouse Name
		
		// Inserting Row
		db.insert(TABLE_CLIENTS, null, values);
		db.close(); // Closing database connection
	}
	
    // Getting single warehouse
    Warehouse Get_warehouse(int id) {
		SQLiteDatabase db = this.getReadableDatabase();
	
		Cursor cursor = db.query(TABLE_WAREHOUSE, new String[] { KEY_ID,
			KEY_NAME, KEY_ADDRESS}, KEY_ID + "=?",
			new String[] { String.valueOf(id) }, null, null, null, null);
		if (cursor != null)
		    cursor.moveToFirst();
	
		Warehouse warehouse = new Warehouse(Integer.parseInt(cursor.getString(0)),
			cursor.getString(1), cursor.getString(2));
		// return contact
		cursor.close();
		db.close();
	
		return warehouse;
    }
    
    // Getting single product
    Product Get_product(int id) {
		SQLiteDatabase db = this.getReadableDatabase();
		
		Cursor cursor = db.query(TABLE_PRODUCTS, new String[] { KEY_PROD_ID,
		KEY_PROD_NAME, KEY_PROD_MEASUREMENT}, KEY_PROD_ID + "=?",
			new String[] { String.valueOf(id) }, null, null, null, null);
		if (cursor != null)
		    cursor.moveToFirst();
	
		Product product = new Product(Integer.parseInt(cursor.getString(0)),
			cursor.getString(1), Integer.parseInt(cursor.getString(2)));
		// return contact
		cursor.close();
		db.close();
	
		return product;
    }
    
    // Getting single client
    Client Get_client(int id) {
		SQLiteDatabase db = this.getReadableDatabase();
		
		Cursor cursor = db.query(TABLE_CLIENTS, new String[] { KEY_CLIENT_ID,
				KEY_CLIENT_NAME, KEY_CLIENT_ADDRESS,KEY_CLIENT_NOTE}, KEY_CLIENT_ID + "=?",
			new String[] { String.valueOf(id) }, null, null, null, null);
		if (cursor != null)
		    cursor.moveToFirst();
	
		Client client = new Client(Integer.parseInt(cursor.getString(0)),
			cursor.getString(1), cursor.getString(2),cursor.getString(3));
		// return contact
		cursor.close();
		db.close();
	
		return client;
    }
    
    
    // Getting All warehouses
    public ArrayList<Warehouse> Get_Warehouses() {
		try {
		    warehouse_list.clear();
	
		    // Select All Query
		    String selectQuery = "SELECT  * FROM " + TABLE_WAREHOUSE;
	
		    SQLiteDatabase db = this.getWritableDatabase();
		    Cursor cursor = db.rawQuery(selectQuery, null);
	
		    // looping through all rows and adding to list
		    if (cursor.moveToFirst()) {
			do {
				Warehouse warehouse = new Warehouse();
				warehouse.set_warehouse(Integer.parseInt(cursor.getString(0)));
				warehouse.set_name(cursor.getString(1));
				warehouse.set_address(cursor.getString(2));
			   
			    // Adding warehouse to list
				warehouse_list.add(warehouse);
			} while (cursor.moveToNext());
		   }
	
		    // return warehouse list
		    cursor.close();
		    db.close();
		    return warehouse_list;
		} catch (Exception e) {
		    // TODO: handle exception
		    Log.e("all_contact", "" + e);
		}

	return warehouse_list;
    }
    
 // Getting All products
    public ArrayList<Product> Get_Products() {
		try {
		    product_list.clear();
	
		    // Select All Query
		    String selectQuery = "SELECT  * FROM " + TABLE_PRODUCTS;
	
		    SQLiteDatabase db = this.getWritableDatabase();
		    Cursor cursor = db.rawQuery(selectQuery, null);
	
		    // looping through all rows and adding to list
		    if (cursor.moveToFirst()) {
			do {
				Product product= new Product();
				product.set_product(Integer.parseInt(cursor.getString(0)));
				product.set_name(cursor.getString(1));
				product.set_measurement(Integer.parseInt(cursor.getString(2)));
			   
			    // Adding warehouse to list
				product_list.add(product);
			} while (cursor.moveToNext());
		   }
	
		    // return warehouse list
		    cursor.close();
		    db.close();
		    return product_list;
		} catch (Exception e) {
		    // TODO: handle exception
		    Log.e("all_products", "" + e);
		}

	return product_list;
    }
    
    // Getting All clients
    public ArrayList<Client> Get_clients() {
		try {
		    client_list.clear();
	
		    // Select All Query
		    String selectQuery = "SELECT  * FROM " + TABLE_CLIENTS;
	
		    SQLiteDatabase db = this.getWritableDatabase();
		    Cursor cursor = db.rawQuery(selectQuery, null);
	
		    // looping through all rows and adding to list
		    if (cursor.moveToFirst()) {
			do {
				Client client= new Client();
				client.setClient(Integer.parseInt(cursor.getString(0)));
				client.setName(cursor.getString(1));
				client.setAddress(cursor.getString(2));
			    client.setNotes(cursor.getString(3));
			    
			    // Adding clients to list
			    client_list.add(client);
			} while (cursor.moveToNext());
		   }
	
		    // return client list
		    cursor.close();
		    db.close();
		    return client_list;
		} catch (Exception e) {
		    // TODO: handle exception
		    Log.e("all_clients", "" + e);
		}

	return client_list;
    }
    
    
    // Updating single warehouse
    public int Update_warehouse(Warehouse warehouse) {
		SQLiteDatabase db = this.getWritableDatabase();
	
		ContentValues values = new ContentValues();
		values.put(KEY_NAME, warehouse.get_name());
		values.put(KEY_ADDRESS, warehouse.get_address());
		
		// updating row
		return db.update(TABLE_WAREHOUSE, values, KEY_ID + " = ?",
			new String[] { String.valueOf(warehouse.get_warehouse()) });
    }
   
    // Updating single product
    public int Update_product(Product product) {
		SQLiteDatabase db = this.getWritableDatabase();
	
		ContentValues values = new ContentValues();
		values.put(KEY_PROD_NAME, product.get_name());
		values.put(KEY_PROD_MEASUREMENT, product.get_measurement());
		
		// updating row
		return db.update(TABLE_PRODUCTS, values, KEY_PROD_ID + " = ?",
			new String[] { String.valueOf(product.get_product()) });
    }
    
    // Updating single Client
    public int Update_client(Client client) {
		SQLiteDatabase db = this.getWritableDatabase();
	
		ContentValues values = new ContentValues();
		values.put(KEY_CLIENT_NAME, client.getName());
		values.put(KEY_CLIENT_ADDRESS, client.getAddress());
		values.put(KEY_CLIENT_NOTE, client.getNotes());
		
		// updating row
		return db.update(TABLE_CLIENTS, values, KEY_CLIENT_ID + " = ?",
			new String[] { String.valueOf(client.getClient())});
    }
    
    
    // Deleting single warehouse
    public void Delete_warehouse(int id) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_WAREHOUSE, KEY_ID + " = ?",
			new String[] { String.valueOf(id) });
		db.close();
    }
    
    // Deleting single product
    public void Delete_product(int id) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_PRODUCTS, KEY_PROD_ID + " = ?",
			new String[] { String.valueOf(id) });
		db.close();
    }
    
    // Deleting single client
    public void Delete_client(int id) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_CLIENTS, KEY_CLIENT_ID + " = ?",
			new String[] { String.valueOf(id) });
		db.close();
    }
    
    // Getting warehouse Count
    public int Get_Total_Warehouse() {
		String countQuery = "SELECT  * FROM " + TABLE_WAREHOUSE;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		cursor.close();
	
		// return count
		return cursor.getCount();
    }
    
}
