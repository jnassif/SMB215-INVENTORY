package com.example.com.inventory;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {
Button warehouseBtn;
Button clientBtn;
Button Product;
Button order;
Button delivery;
Button invoice;
Intent warehouseActivity;
Intent clientActivity;
Intent productActivity;
Intent inventoryActivity;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		warehouseBtn = (Button) findViewById(R.id.warehouseBtn);
		clientBtn 	 = (Button) findViewById(R.id.clientBtn);
		Product 	 = (Button) findViewById(R.id.productBtn);
		order 	 	 = (Button) findViewById(R.id.OrderBtn);
		delivery 	 = (Button) findViewById(R.id.deliveryBtn);
		invoice 	 = (Button) findViewById(R.id.invoiceBtn);
		
		
		//on click open warehouse activity 
		warehouseBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//go to warehouse activity
				warehouseActivity = new Intent(MainActivity.this,WarehouseActivity.class);
				startActivity(warehouseActivity);
			}
		});
		
		//on click open warehouse activity 
		clientBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//go to warehouse activity
				clientActivity = new Intent(MainActivity.this,ClientActivity.class);
				startActivity(clientActivity);
			}
		});
		
		//on click open warehouse activity 
		Product.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//go to warehouse activity
				productActivity = new Intent(MainActivity.this,ProductActivity.class);
				startActivity(productActivity);
			}
		});
		
		order.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				inventoryActivity = new Intent(MainActivity.this,InventoryActivity.class);
				PreferenceManager.getDefaultSharedPreferences(MainActivity.this).edit().putString("condition","0").commit();  
				
				startActivity(inventoryActivity);
			}
		});
		
		delivery.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				inventoryActivity = new Intent(MainActivity.this,InventoryActivity.class);
				PreferenceManager.getDefaultSharedPreferences(MainActivity.this).edit().putString("condition","1").commit();
				startActivity(inventoryActivity);
			}
		});
		
		invoice.setOnClickListener(new View.OnClickListener() {
		
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				inventoryActivity = new Intent(MainActivity.this,InventoryActivity.class);
				PreferenceManager.getDefaultSharedPreferences(MainActivity.this).edit().putString("condition","2").commit();
				startActivity(inventoryActivity);
			}
		});	
		
		
	}
	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
