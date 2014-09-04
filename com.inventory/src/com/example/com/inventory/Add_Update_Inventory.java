package com.example.com.inventory;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

public class Add_Update_Inventory extends Activity{
	
	Spinner add_clients;
	Spinner add_warehouse;
	Spinner add_paymode;
	DatePicker datePicker;
    Button add_save_btn, add_view_all, update_btn, update_view_all;
    LinearLayout add_view, update_view;
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
	} else {

	    update_view.setVisibility(View.VISIBLE);
	    add_view.setVisibility(View.GONE);
	    INVENTORY_ID = Integer.parseInt(getIntent().getStringExtra("INVENTORY_ID"));
	    

	    Inventory c = dbHandler.Get_inventory(INVENTORY_ID);

	    ArrayAdapter<Inventory> measurement_array= new ArrayAdapter<Inventory>(Add_Update_Inventory.this,android.R.layout.simple_spinner_item, dbHandler.Get_inventories());
	    
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
	    	Log.e("c"+client_id+",ware"+warehouse_id+",date:"+NATURE,String.valueOf(today.monthDay+"/"+today.month+"/"+today.year)+",pay"+pay_mode);
	    	
	    	
	    	dbHandler.Add_inventory(new Inventory(INVENTORY_ID,client_id,NATURE,String.valueOf(today.monthDay+"/"+today.month+"/"+today.year),warehouse_id,pay_mode));
		    Toast_msg = "Data inserted successfully";
		    Show_Toast(Toast_msg);
		    Reset_Text();
	    }
	});

	update_btn.setOnClickListener(new View.OnClickListener() {

	    @Override
	    public void onClick(View v) {
		// TODO Auto-generated method stub

    	Client inc = (Client) add_clients.getSelectedItem();
    	String invDate =String.valueOf(datePicker.getYear()+'/'+datePicker.getMonth()+'/'+datePicker.getDayOfMonth());
	    Warehouse war= (Warehouse) add_warehouse.getSelectedItem();
	    	
		
		// check the value state is null or not
		if (valid_name != null  ) {

		    dbHandler.Update_inventory(new Inventory(INVENTORY_ID,inc.getClient(),0,invDate,war.get_warehouse(),add_paymode.getSelectedItemPosition() ));
		    dbHandler.close();
		    Toast_msg = "Data Update successfully";
		    Show_Toast(Toast_msg);
		    Reset_Text();
		} else {
		    Toast_msg = "Sorry Some Fields are missing.\nPlease Fill up all.";
		    Show_Toast(Toast_msg);
		}

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
		
		String[] test=new String[]{"Check","Cash","Visa"};
		ArrayAdapter<String> paymode_array= new ArrayAdapter<String>(Add_Update_Inventory.this,android.R.layout.simple_spinner_item, test);
		ArrayAdapter<Client>client_array= new ArrayAdapter<Client>(Add_Update_Inventory.this,android.R.layout.simple_spinner_item, dbHandler.Get_clients());
		ArrayAdapter<Warehouse>warehouse_array= new ArrayAdapter<Warehouse>(Add_Update_Inventory.this,android.R.layout.simple_spinner_item, dbHandler.Get_Warehouses());
		
		//populate spinner
		add_paymode.setAdapter(paymode_array);
		add_clients.setAdapter(client_array );
		add_warehouse.setAdapter(warehouse_array );
		
		add_save_btn = (Button) findViewById(R.id.add_save_btn);
		
		update_btn = (Button) findViewById(R.id.update_btn);
		add_view_all = (Button) findViewById(R.id.add_view_all);
		update_view_all = (Button) findViewById(R.id.update_view_all);
	
		add_view = (LinearLayout) findViewById(R.id.add_view);
		update_view = (LinearLayout) findViewById(R.id.update_view);
	
		add_view.setVisibility(View.GONE);
		update_view.setVisibility(View.GONE);

    }

 
    public void Show_Toast(String msg) {
	Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
    }

    public void Reset_Text() {

		
		
	

    }
}
