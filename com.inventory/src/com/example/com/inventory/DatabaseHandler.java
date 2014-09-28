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
    private static final int DATABASE_VERSION = 2;

    // Database Name
    private static final String DATABASE_NAME = "inventory";

    // Contacts, products , client table name
    private static final String TABLE_WAREHOUSE = "warehouse";
    private static final String TABLE_PRODUCTS = "products";
    private static final String TABLE_CLIENTS = "clients";
    private static final String TABLE_INVENTORY = "inventory";
    private static final String TABLE_INVENTORY_DETAIL = "inventory_detail";
    
    // Contacts Table Columns names
    private static final String KEY_ID = "warehouse";
    private static final String KEY_NAME = "name";
    private static final String KEY_ADDRESS = "address";
    private final ArrayList<Warehouse> warehouse_list = new ArrayList<Warehouse>();
    
    //products Table Columns names
    private static final String KEY_PROD_ID = "product";
    private static final String KEY_PROD_NAME = "name";
    private static final String KEY_PROD_MEASUREMENT = "measurement";
    private static final String KEY_PROD_QTY = "quantity";
    private final ArrayList<Product> product_list = new ArrayList<Product>();

    //Clients Table Columns names
    private static final String KEY_CLIENT_ID = "client";
    private static final String KEY_CLIENT_NAME = "name";
    private static final String KEY_CLIENT_ADDRESS = "address";
    private static final String KEY_CLIENT_NOTE = "note";
    private final ArrayList<Client> client_list = new ArrayList<Client>();
    
    //inventory table
    private static final String KEY_INV_ID = "inventory";
    private static final String KEY_INV_CLIENT = "client";
    private static final String KEY_INV_NATURE = "nature";
    private static final String KEY_INV_THEDATE = "thedate";
    private static final String KEY_INV_WARHOUSE = "warehouse";
    private static final String KEY_INV_PAYMODE = "payement_mode";
    private static final String KEY_INV_OS = "os";
    private static final String KEY_INV_DOC_NUM = "doc_num";
    private final ArrayList<Inventory> inventory_list = new ArrayList<Inventory>();
    
    //inventoryDet table
    private static final String KEY_INVDET_ID = "inventory";
    private static final String KEY_INVDET_PRODUCT = "product";
    private static final String KEY_INVDET_QTY = "quantity";
    private static final String KEY_INVDET_AMOUNT = "amount";
    private final ArrayList<InventoryDetail> inventoryDet_list = new ArrayList<InventoryDetail>();
    
    
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
		+ KEY_PROD_MEASUREMENT + " INTEGER, " + KEY_PROD_QTY + " FLOAT "+ ")";
		db.execSQL(CREATE_PRODUCT_TABLE);
	
		//create client table
		String CREATE_CLIENT_TABLE = "CREATE TABLE " + TABLE_CLIENTS + "("
		+ KEY_CLIENT_ID + " INTEGER PRIMARY KEY," + KEY_CLIENT_NAME + " TEXT,"
		+ KEY_CLIENT_ADDRESS + " TEXT," + KEY_CLIENT_NOTE  + " TEXT " +  ")";
		db.execSQL(CREATE_CLIENT_TABLE);
		
	   
		//create inventory table
		String CREATE_INVENTORY_TABLE = "CREATE TABLE " + TABLE_INVENTORY + "("
		+ KEY_INV_ID + " INTEGER PRIMARY KEY," + KEY_INV_CLIENT + " INTEGER,"
		+ KEY_INV_NATURE + " INTEGER," + KEY_INV_THEDATE  + " TEXT ," + KEY_INV_WARHOUSE + " INTEGER ,"
		+ KEY_INV_PAYMODE + " INTEGER, " + KEY_INV_OS + " INTEGER, " + KEY_INV_DOC_NUM + " INTEGER " + ")";
		db.execSQL(CREATE_INVENTORY_TABLE);
		
		//create inventory detail table
		String CREATE_INVDET_TABLE = "CREATE TABLE " + TABLE_INVENTORY_DETAIL + "("
		+ KEY_INVDET_ID + " INTEGER ," + KEY_INVDET_PRODUCT + " INTEGER ,"
		+ KEY_INVDET_QTY + " INTEGER," + KEY_INVDET_AMOUNT  + " integer," + "PRIMARY KEY ("+KEY_INVDET_ID+","+ KEY_INVDET_PRODUCT+")"+ ")";
		db.execSQL(CREATE_INVDET_TABLE);
	
    }

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_WAREHOUSE);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTS);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_CLIENTS);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_INVENTORY);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_INVENTORY_DETAIL);
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
		values.put(KEY_PROD_QTY, product.get_price());//get product price
		
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
	
	// Adding new Inventory
	public void Add_inventory(Inventory inventory) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		ContentValues values1 = new ContentValues();
		
		values.put(KEY_INV_CLIENT, inventory.get_client()); // warehouse address Phone
		values.put(KEY_INV_NATURE, inventory.get_nature()); // warehouse Name
		values.put(KEY_INV_THEDATE, inventory.get_theDate()); // warehouse Name
		values.put(KEY_INV_WARHOUSE, inventory.get_warehouse()); // warehouse Nam
		
		values.put(KEY_INV_PAYMODE, inventory.get_paymode()); // warehouse Name
		values.put(KEY_INV_OS, inventory.get_os()); // warehouse Name
		values.put(KEY_INV_DOC_NUM, inventory.get_docNum()); // warehouse Name
		if(inventory.get_docNum() > 0 ){
			//set outstanding field = to 1 
			values1.put(KEY_INV_OS,0);
			db.update(TABLE_INVENTORY,values1 , KEY_INV_ID +" = " + inventory.get_docNum() , null);
		}
		// Inserting Row
		db.insert(TABLE_INVENTORY, null, values);
		db.close(); // Closing database connection
	}
	
	
	// Adding new Inventory Detail
	public void Add_inventory_detail(InventoryDetail inventoryDet) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(KEY_INVDET_ID,		inventoryDet.getInventory()); 
		values.put(KEY_INVDET_PRODUCT,  inventoryDet.getProduct()); 
		values.put(KEY_INVDET_QTY, 		inventoryDet.getQuantity()); 
		values.put(KEY_INVDET_AMOUNT, 	inventoryDet.getAmount()); 
		
		// Inserting Row
		db.insert(TABLE_INVENTORY_DETAIL, null, values);
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
		KEY_PROD_NAME, KEY_PROD_MEASUREMENT,KEY_PROD_QTY}, KEY_PROD_ID + "=?",
			new String[] { String.valueOf(id) }, null, null, null, null);
		if (cursor != null)
		    cursor.moveToFirst();
	
		Product product = new Product(Integer.parseInt(cursor.getString(0)),
			cursor.getString(1), Integer.parseInt(cursor.getString(2)),Float.parseFloat(cursor.getString(3)));
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
    
    
    // Getting single inventory
    Inventory Get_inventory(int id) {
		SQLiteDatabase db = this.getReadableDatabase();
		
		Cursor cursor = db.query(TABLE_INVENTORY, new String[] { KEY_INV_ID,
				KEY_INV_CLIENT, KEY_INV_NATURE,KEY_INV_THEDATE,KEY_INV_WARHOUSE,KEY_INV_PAYMODE,KEY_INV_OS,KEY_INV_DOC_NUM
				}, KEY_INV_ID + "=?",
			new String[] { String.valueOf(id) }, null, null, null, null);
		if (cursor != null)
		    cursor.moveToFirst();
	
		Inventory inventory = new Inventory(Integer.parseInt(cursor.getString(0)),
			Integer.parseInt(cursor.getString(1)), Integer.parseInt(cursor.getString(2)),cursor.getString(3),Integer.parseInt(cursor.getString(4)),Integer.parseInt(cursor.getString(5)),Integer.parseInt(cursor.getString(6)),Integer.parseInt(cursor.getString(7)));
		// return contact
		cursor.close();
		db.close();
	
		return inventory;
    }
    
    // Getting single inventory detail
    InventoryDetail Get_inventoryDet(int id) {
		SQLiteDatabase db = this.getReadableDatabase();
		
		Cursor cursor = db.query(TABLE_INVENTORY_DETAIL, new String[] { KEY_INVDET_ID,
				KEY_INVDET_PRODUCT, KEY_INVDET_QTY,KEY_INVDET_AMOUNT}, KEY_INV_ID + "=?",
			new String[] { String.valueOf(id) }, null, null, null, null);
		if (cursor != null)
		    cursor.moveToFirst();
	
		InventoryDetail inventoryDet = new InventoryDetail(
				Integer.parseInt(cursor.getString(0)),
					Integer.parseInt(cursor.getString(1)),
					Integer.parseInt(cursor.getString(2)),
					Float.parseFloat(cursor.getString(3))
		);
		// return contact
		cursor.close();
		db.close();
	
		return inventoryDet;
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
				product.set_price(Integer.parseInt(cursor.getString(3)));
			   
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
    
 // Getting All outstanding inv
    public ArrayList<Inventory> Get_inv(int nature ) {
		try {
		    product_list.clear();
		    String selectQuery = "";
		   
		    if(nature == 1){
			    // Select All Query
			    selectQuery = "SELECT  * FROM " + TABLE_INVENTORY + " WHERE " + KEY_INV_OS + " = 1 and " + KEY_INV_NATURE + " = 0 " ;
		    }else if(nature == 2 ){
				selectQuery = "SELECT  * FROM " + TABLE_INVENTORY + " WHERE " + KEY_INV_OS + " = 1 and " + KEY_INV_NATURE + " IN (0,1) " ;	
			 }
			   
		    SQLiteDatabase db = this.getWritableDatabase();
		    Cursor cursor = db.rawQuery(selectQuery, null);
		   
		    // looping through all rows and adding to list
		    if (cursor.moveToFirst()) {
				do {
					Inventory inventory= new Inventory();
					inventory.set_inventory(Integer.parseInt(cursor.getString(0)));
					
					// Adding clients to list
					inventory_list.add(inventory);
				} while (cursor.moveToNext());
		   }
		    
		    // return client list
		    cursor.close();
		    db.close();
		    return inventory_list;
		} catch (Exception e) {
		    // TODO: handle exception
		    Log.e("all_inventories", "" + e);
		}

	return inventory_list;
    }
        
 // Getting All inventories
    public ArrayList<Inventory> Get_inventories(int nature) {
		try {
			inventory_list.clear();
	
		    // Select All Query
		    String selectQuery = "SELECT  * FROM " + TABLE_INVENTORY + " WHERE "+ KEY_INV_NATURE+"="+nature;
	
		    SQLiteDatabase db = this.getWritableDatabase();
		    Cursor cursor = db.rawQuery(selectQuery, null);
	
		    // looping through all rows and adding to list
		    if (cursor.moveToFirst()) {
			do {
				Inventory inventory= new Inventory();
				inventory.set_inventory(Integer.parseInt(cursor.getString(0)));
				inventory.set_client(Integer.parseInt(cursor.getString(1)));
				inventory.set_nature(Integer.parseInt(cursor.getString(2)));
				inventory.set_theDate(cursor.getString(3));
			
				inventory.set_warehouse(Integer.parseInt(cursor.getString(4)));
				
				inventory.set_paymode(Integer.parseInt(cursor.getString(5)));
				
			    
			    // Adding clients to list
				inventory_list.add(inventory);
			} while (cursor.moveToNext());
		   }
	
		    // return client list
		    cursor.close();
		    db.close();
		    return inventory_list;
		} catch (Exception e) {
		    // TODO: handle exception
		    Log.e("all_inventories", "" + e);
		}

	return inventory_list;
    }
    

	 // Getting All inventories detail
    public ArrayList<InventoryDetail> Get_inventoriesDet(int inventory) {
		try {
			inventoryDet_list.clear();
	
		    // Select All Query
		    String selectQuery = "SELECT  * FROM " + TABLE_INVENTORY_DETAIL + " where "+ KEY_INVDET_ID +" = "+inventory;
	
		    SQLiteDatabase db = this.getWritableDatabase();
		    Cursor cursor = db.rawQuery(selectQuery, null);
	
		    // looping through all rows and adding to list
		    if (cursor.moveToFirst()) {
			do {
				InventoryDetail inventoryDet= new InventoryDetail();
				inventoryDet.setInventory(Integer.parseInt(cursor.getString(0)));
				inventoryDet.setProduct(Integer.parseInt(cursor.getString(1)));
				inventoryDet.setQuantity(Integer.parseInt(cursor.getString(2)));
				inventoryDet.setAmount(Float.parseFloat(cursor.getString(3)));
				
			    
			    // Adding clients to list
				inventoryDet_list.add(inventoryDet);
			} while (cursor.moveToNext());
		   }
	
		    // return client list
		    cursor.close();
		    db.close();
		    return inventoryDet_list;
		} catch (Exception e) {
		    // TODO: handle exception
		    Log.e("all_inventoriesDet", "" + e);
		}

	return inventoryDet_list;
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
		values.put(KEY_PROD_QTY, product.get_price());
		
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
    

    // Updating single inventory
    public int Update_inventory(Inventory inventory) {
		SQLiteDatabase db = this.getWritableDatabase();
	
		ContentValues values = new ContentValues();
		values.put(KEY_INV_CLIENT, inventory.get_client());
		values.put(KEY_INV_NATURE, inventory.get_nature());
		values.put(KEY_INV_THEDATE, inventory.get_theDate());
		values.put(KEY_INV_WARHOUSE, inventory.get_warehouse());
		values.put(KEY_INV_PAYMODE, inventory.get_paymode());
		values.put(KEY_INV_DOC_NUM, inventory.get_docNum());
		
		// updating row
		return db.update(TABLE_INVENTORY, values, KEY_INV_ID + " = ?",
			new String[] { String.valueOf(inventory.get_inventory())});
    }
    
 // Updating single inventory detail
    public int Update_inventoryDet(InventoryDetail inventoryDet) {
		SQLiteDatabase db = this.getWritableDatabase();
	
		ContentValues values = new ContentValues();
		values.put(KEY_INVDET_PRODUCT, inventoryDet.getProduct());
		values.put(KEY_INVDET_QTY, inventoryDet.getQuantity());
		values.put(KEY_INVDET_AMOUNT, inventoryDet.getAmount());
		
		// updating row
		return db.update(TABLE_INVENTORY_DETAIL, values, KEY_INVDET_ID + " = ?",
			new String[] { String.valueOf(inventoryDet.getInventory())});
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
    
   // Deleting single inventory
    public void Delete_inventory(int id) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_INVENTORY, KEY_INV_ID + " = ?",
			new String[] { String.valueOf(id) });
		db.close();
	
    }
    
  // Deleting single inventory detail
    public void Delete_inventory_detail(int inv_id , int prod_id) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_INVENTORY_DETAIL, KEY_INVDET_ID + " = ?" + KEY_INVDET_PRODUCT + " =? ",
			new String[] { String.valueOf(inv_id),String.valueOf(prod_id) });
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
