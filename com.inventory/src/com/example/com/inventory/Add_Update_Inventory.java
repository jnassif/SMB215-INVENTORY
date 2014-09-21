package com.example.com.inventory;

import java.util.ArrayList;

import com.example.com.inventory.ProductActivity.Product_Adapter;
import com.example.com.inventory.ProductActivity.Product_Adapter.UserHolder;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Add_Update_Inventory extends Activity{
	
	Spinner add_clients;
	Spinner add_warehouse;
	Spinner add_paymode;
	DatePicker datePicker;
	ArrayList<InventoryDetail> product_data = new ArrayList<InventoryDetail>();
	Spinner  products_spiner;
	EditText prodQtyEditTxt;
	EditText prodAmount;
	ListView product_listview;
	Product_Adapter cAdapter;
	Button add_save_prod_btn;
	TextView amountTxt;
	String meas;
	DatabaseHandler db;
    Button add_save_btn, add_view_all, update_btn, update_view_all;
    LinearLayout add_view, update_view,add_products_view;
    String valid_mob_number = null, valid_address = null, valid_name = null,
	    Toast_msg = null, valid_user_id = "",valid_notes = "" ;
    int INVENTORY_ID;
    int NATURE;
    int paymode ;
    
    DatabaseHandler dbHandler = new DatabaseHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_update_inventory);
	
		// set screen
		Set_Add_Update_Screen();

		// set visibility of view as per calling activity
		String called_from = getIntent().getStringExtra("called");
		NATURE = Integer.parseInt(getIntent().getStringExtra("inv_nature"));
		
	if (called_from.equalsIgnoreCase("add")) {
	    add_view.setVisibility(View.VISIBLE);
	    update_view.setVisibility(View.GONE);
	    add_products_view.setVisibility(View.GONE);
	} else {
			
	    update_view.setVisibility(View.VISIBLE);
	    add_view.setVisibility(View.GONE);
	    INVENTORY_ID = Integer.parseInt(getIntent().getStringExtra("INVENTORY_ID"));
	    Inventory c = dbHandler.Get_inventory(INVENTORY_ID);
	    add_paymode.setSelection(c.get_paymode());
	    Set_Referash_Data();

	  
	    
	    // dbHandler.close();
	}
	
	
	add_save_btn.setOnClickListener(new View.OnClickListener() {

	    @Override
	    public void onClick(View v) {
			
	    	//get client
	    	String[] client_inf 	= add_clients.getSelectedItem().toString().split("-");
	    	int client_id = Integer.parseInt(client_inf[0]);
	    	//get warehouse
	    	String[] warehouse_inf 	= add_warehouse.getSelectedItem().toString().split("-");
	    	int warehouse_id = Integer.parseInt(warehouse_inf[0]);
	    	//get date 
	    	Time today = new Time(Time.getCurrentTimezone());
	    	today.setToNow();
	    	//get paymode
	    	int pay_mode = add_paymode.getSelectedItemPosition();
	    	Log.e("c"+client_id+",ware"+warehouse_id+",date:"+NATURE,String.valueOf(today.monthDay+"/"+today.month+"/"+today.year)+",pay:"+pay_mode);
	    	Log.e("INVENTORY_ID",String.valueOf(INVENTORY_ID));
	    	
	    	dbHandler.Add_inventory(new Inventory(INVENTORY_ID,client_id,NATURE,String.valueOf(today.monthDay+"/"+today.month+"/"+today.year),warehouse_id,pay_mode));
		    Toast_msg = "Data inserted successfully";
		    Show_Toast(Toast_msg);
		    Reset_Text();
	    }
	});
	
	add_save_prod_btn.setOnClickListener(new View.OnClickListener() {

	    @Override
	    public void onClick(View v) {
			//get inventory id 
	    	//get the product id
	    	String[] product_inf 	= products_spiner.getSelectedItem().toString().split("-");
	    	int product_id 	= Integer.parseInt(product_inf[0]);
	    	
	    	//qty 
	    	int prodQty = Integer.parseInt(prodQtyEditTxt.getText().toString());
	    	
	    	//amount
	    	Float amount = Float.parseFloat(amountTxt.getText().toString());
	    	
	    	dbHandler.Add_inventory_detail(new InventoryDetail(INVENTORY_ID,product_id,prodQty,amount));
		    Toast_msg = "Product inserted successfully";
		    Show_Toast(Toast_msg);
		    Reset_Text();
		    Set_Referash_Data();
	    }
	});

	prodQtyEditTxt.addTextChangedListener(new TextWatcher(){

		@Override
		public void afterTextChanged(Editable arg0) {
			// TODO Auto-generated method stub
			String[] product_inf 	= products_spiner.getSelectedItem().toString().split("-");
	    	int product_id 	= Integer.parseInt(product_inf[0]);
	    	
	    	//get product price 
	    	Product prod = dbHandler.Get_product(product_id);
	    	String qty = prodQtyEditTxt.getText().toString();
	    	if (qty.isEmpty() || qty == null){
	    		qty = "0";
	    	}
	    	
	    	//set amount 
	    	Float amount = Float.parseFloat(qty) * prod.get_price();
	    	
	    	amountTxt.setText(amount.toString());
	    	
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			// TODO Auto-generated method stub
			
		}});
	
	update_btn.setOnClickListener(new View.OnClickListener() {

	    @Override
	    public void onClick(View v) {
		// TODO Auto-generated method stub

    
    	//get client
    	String[] client_inf 	= add_clients.getSelectedItem().toString().split("-");
    	int client_id = Integer.parseInt(client_inf[0]);
    	//get warehouse
    	String[] warehouse_inf 	= add_warehouse.getSelectedItem().toString().split("-");
    	int warehouse_id = Integer.parseInt(warehouse_inf[0]);
    	//get date 
    	Time today = new Time(Time.getCurrentTimezone());
    	today.setToNow();
    	//get paymode
    	int pay_mode = add_paymode.getSelectedItemPosition();
    	Log.e("c"+client_id+",ware"+warehouse_id+",date:"+NATURE,String.valueOf(today.monthDay+"/"+today.month+"/"+today.year)+",pay:"+pay_mode);
    	Log.e("INVENTORY_ID",String.valueOf(INVENTORY_ID));
	    
	    
	    dbHandler.Update_inventory(new Inventory(INVENTORY_ID,client_id,NATURE,String.valueOf(today.monthDay+"/"+today.month+"/"+today.year),warehouse_id,pay_mode ));
	    dbHandler.close();
	    Toast_msg = "Data Update successfully";
	    Show_Toast(Toast_msg);
	    Reset_Text();
		
	    }
	});
	update_view_all.setOnClickListener(new View.OnClickListener() {

	    @Override
	    public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent view_user = new Intent(Add_Update_Inventory.this,InventoryActivity.class);
		view_user.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
			| Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(view_user);
		finish();
	    }
	});

	
	
	add_view_all.setOnClickListener(new View.OnClickListener() {

	    @Override
	    public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent view_user = new Intent(Add_Update_Inventory.this,InventoryActivity.class);
		view_user.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
			| Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(view_user);
		finish();
	    }
	});

    }

    public void Set_Add_Update_Screen() {

		add_paymode = (Spinner) findViewById(R.id.paymode_spinner);
		add_clients = (Spinner) findViewById(R.id.client_spiner);
		add_warehouse = (Spinner) findViewById(R.id.warehouseSpinner);
		
	    product_listview = (ListView) findViewById(R.id.productList);
		product_listview.setItemsCanFocus(false);
		
		//elements to add a new product
		products_spiner = (Spinner) findViewById(R.id.product_spiner);
		prodQtyEditTxt = (EditText) findViewById(R.id.prodQtyEditTxt);
		amountTxt = (TextView) findViewById(R.id.amountTxt);
		
		//add new product to inventory
		add_save_prod_btn = (Button) findViewById(R.id.add_save_prod_btn);
		
		String[] test=new String[]{"Check","Cash","Visa"};
		ArrayAdapter<String> paymode_array= new ArrayAdapter<String>(Add_Update_Inventory.this,android.R.layout.simple_spinner_item, test);
		ArrayAdapter<Client>client_array= new ArrayAdapter<Client>(Add_Update_Inventory.this,android.R.layout.simple_spinner_item, dbHandler.Get_clients());
		ArrayAdapter<Warehouse>warehouse_array= new ArrayAdapter<Warehouse>(Add_Update_Inventory.this,android.R.layout.simple_spinner_item, dbHandler.Get_Warehouses());
		ArrayAdapter<Product>products_array = new ArrayAdapter<Product>(Add_Update_Inventory.this,android.R.layout.simple_spinner_item, dbHandler.Get_Products());
		
		
		//populate spinner
		add_paymode.setAdapter(paymode_array);
		add_clients.setAdapter(client_array );
		add_warehouse.setAdapter(warehouse_array );
		products_spiner.setAdapter(products_array );
		
		add_save_btn = (Button) findViewById(R.id.add_save_btn);
		
		update_btn = (Button) findViewById(R.id.update_btn);
		add_view_all = (Button) findViewById(R.id.add_view_all);
		update_view_all = (Button) findViewById(R.id.update_view_all);
		
		add_products_view = (LinearLayout) findViewById(R.id.add_products_view);
		add_view = (LinearLayout) findViewById(R.id.add_view);
		update_view = (LinearLayout) findViewById(R.id.update_view);
	
		add_view.setVisibility(View.GONE);
		update_view.setVisibility(View.GONE);

    }
    public void Set_Referash_Data() {
    	// remove all the elements from the array 
    	product_data.clear();
		db = new DatabaseHandler(this);
		ArrayList<InventoryDetail> Product_array_from_db = db.Get_inventoriesDet(INVENTORY_ID);
	
		for (int i = 0; i < Product_array_from_db.size(); i++) {
	
		    int product   =     Product_array_from_db.get(i).getProduct();
		    int quantity  =     Product_array_from_db.get(i).getQuantity();
		    Float amount    = 	Product_array_from_db.get(i).getAmount();
		    
		    InventoryDetail cnt = new InventoryDetail();
		    cnt.setProduct(product);
		    cnt.setQuantity(quantity);
		    cnt.setAmount(amount);
		    
		    product_data.add(cnt);
		}
		db.close();
		cAdapter = new Product_Adapter(Add_Update_Inventory.this, R.layout.listview_row_prod,product_data);
		product_listview.setAdapter(cAdapter);
		cAdapter.notifyDataSetChanged();
    }
    
    //********************* Populate all the products  in the list *********************
    public class Product_Adapter extends ArrayAdapter<InventoryDetail> {
		Activity activity;
		int layoutResourceId;
		InventoryDetail product;
		ArrayList<InventoryDetail> data = new ArrayList<InventoryDetail>();

		public Product_Adapter(Activity act, int layoutResourceId,ArrayList<InventoryDetail> data) {
		    super(act, layoutResourceId, data);
		    this.layoutResourceId = layoutResourceId;
		    this.activity = act;
		    this.data = data;
		    notifyDataSetChanged();
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
		    View row = convertView;
		    UserHolder holder = null;
		    //set the element for the row in the list view 
		    if (row == null) {
				LayoutInflater inflater = LayoutInflater.from(activity);
	
				row = inflater.inflate(layoutResourceId, parent, false);
				holder = new UserHolder();
				holder.name = (TextView) row.findViewById(R.id.user_name_txt);
				holder.measurement = (TextView) row.findViewById(R.id.product_meas);
				holder.product_price = (TextView) row.findViewById(R.id.product_price);
				holder.edit = (Button) row.findViewById(R.id.btn_update);
				holder.delete = (Button) row.findViewById(R.id.btn_delete);
				
				row.setTag(holder);
		    } else {
		    	holder = (UserHolder) row.getTag();
		    }
		    product = data.get(position);
		    //holder.edit.setTag(product.getProduct());
		    //holder.delete.setTag(product.getProduct());
		    //holder.name.setText(product.getQuantity());
		    Product p = db.Get_product(product.getProduct());
		    holder.name.setText(p.get_name());
		    Integer qty = product.getQuantity();
		    
		    Float amount = product.getAmount();
		    holder.measurement.setText(amount.toString());
		    holder.product_price.setText(qty.toString());

		   
		   
		    return row;

		}

		class UserHolder {
		    TextView name;
		    TextView measurement;
		    TextView product_price;
		    Button edit;
		    Button delete;
		}

    }
    
 
    public void Show_Toast(String msg) {
	Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
    }

    public void Reset_Text() {

		
		
	

    }
}
