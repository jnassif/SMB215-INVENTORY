package com.example.com.inventory;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

public class Add_Update_Product extends Activity{
	EditText add_name;
	EditText add_qty;
	Spinner add_measurement;
    Button add_save_btn, add_view_all, update_btn, update_view_all;
    LinearLayout add_view, update_view;
    String valid_mob_number = null, valid_address = null, valid_name = null,
	    Toast_msg = null, valid_user_id = "",valid_notes = "" ;
    int PRODUCT_ID;
    int measurement ;
    DatabaseHandler dbHandler = new DatabaseHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_update_product);
	
		// set screen
		Set_Add_Update_Screen();

		// set visibility of view as per calling activity
		String called_from = getIntent().getStringExtra("called");

	if (called_from.equalsIgnoreCase("add")) {
	    add_view.setVisibility(View.VISIBLE);
	    update_view.setVisibility(View.GONE);
	} else {

	    update_view.setVisibility(View.VISIBLE);
	    add_view.setVisibility(View.GONE);
	    PRODUCT_ID = Integer.parseInt(getIntent().getStringExtra("PRODUCT_ID"));

	    Product c = dbHandler.Get_product(PRODUCT_ID);

	    add_name.setText(c.get_name());
	    
	    add_measurement.setSelection(c.get_measurement());
	    
	    // dbHandler.close();
	}
	

	add_save_btn.setOnClickListener(new View.OnClickListener() {

	    @Override
	    public void onClick(View v) {
		// TODO Auto-generated method stub
		// check the value state is null or not
	    	Log.e("asda","" +add_measurement.getSelectedItemPosition());
	    	System.out.println(add_measurement.getSelectedItemPosition()+"sadlasioidas;ondo;asindo;asj");
		    dbHandler.Add_product(new Product(PRODUCT_ID,add_name.getText().toString(),add_measurement.getSelectedItemPosition(),add_qty.getText().toString()));
		    Toast_msg = "Data inserted successfully";
		    Show_Toast(Toast_msg);
		    Reset_Text();

		

	    }
	});

	update_btn.setOnClickListener(new View.OnClickListener() {

	    @Override
	    public void onClick(View v) {
		// TODO Auto-generated method stub

		valid_name = add_name.getText().toString();
		measurement = add_measurement.getSelectedItemPosition();
		
		// check the value state is null or not
		if (valid_name != null  ) {

		    dbHandler.Update_product(new Product(PRODUCT_ID, valid_name,measurement));
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
		Intent view_user = new Intent(Add_Update_Product.this,ProductActivity.class);
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
		Intent view_user = new Intent(Add_Update_Product.this,ProductActivity.class);
		view_user.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
			| Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(view_user);
		finish();
	    }
	});

    }

    public void Set_Add_Update_Screen() {

		add_name = (EditText) findViewById(R.id.add_name);
		add_measurement = (Spinner) findViewById(R.id.measurement);
		add_qty = (EditText) findViewById(R.id.add_qty);
		
		String[] test=new String[]{"Unit","Kg","L"};
		ArrayAdapter<String> measurement_array= new ArrayAdapter<String>(Add_Update_Product.this,android.R.layout.simple_spinner_item, test);
		//populate spinner
		add_measurement.setAdapter(measurement_array);
		
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

		add_name.getText().clear();
		
	

    }
}
