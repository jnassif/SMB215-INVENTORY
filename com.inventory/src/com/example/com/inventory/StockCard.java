package com.example.com.inventory;

import java.util.ArrayList;

import com.example.com.inventory.ProductActivity.Product_Adapter;
import com.example.com.inventory.ProductActivity.Product_Adapter.UserHolder;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class StockCard extends Activity {
	ListView product_stockCard;
	DatabaseHandler db;
	ArrayList<Stock> stock_data = new ArrayList<Stock>();
	Stock_Adapter stock_adapter;
	TextView total_val;
	int count = 0;
	int result_size = 0;
	
	int start_inv_qty = 0 ,del_qty = 0 , pur_qty = 0, total;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.stock_card);
		db = new DatabaseHandler(this);
		product_stockCard = (ListView) findViewById(R.id.product_stockCard);
		total_val =  (TextView) findViewById(R.id.total_val);
		product_stockCard.setItemsCanFocus(false);
		
		ArrayList<Stock> stock_array = db.getProdStockView(Integer.parseInt(getIntent().getStringExtra("PRODUCT_ID")));
		for (int i = 0; i < stock_array.size(); i++) {
			
		    int qty =     stock_array.get(i).getQty();
		    int nature = stock_array.get(i).getNature();
		    
		    Stock stock = new Stock();
		    stock.setNature(nature);
		    stock.setQty(qty);
		   
		    stock_data.add(stock);
		}
		result_size = stock_array.size();
		db.close();
		stock_adapter = new Stock_Adapter(StockCard.this, R.layout.listview_stock,stock_data);
		product_stockCard.setAdapter(stock_adapter);
		stock_adapter.notifyDataSetChanged();
		
		
	
	}
	
	 //********************* Populate all the stock in the list *********************
    public class Stock_Adapter extends ArrayAdapter<Stock> {
		Activity activity;
		int layoutResourceId;
		Stock stock;
		ArrayList<Stock> data = new ArrayList<Stock>();

		public Stock_Adapter(Activity act, int layoutResourceId,ArrayList<Stock> data) {
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
		    if (row == null ) {
				LayoutInflater inflater = LayoutInflater.from(activity);
	
				row = inflater.inflate(layoutResourceId, parent, false);
				holder = new UserHolder();
				holder.name = (TextView) row.findViewById(R.id.user_name_txt);
				holder.measurement = (TextView) row.findViewById(R.id.product_meas);
				
				row.setTag(holder);
				
				stock = data.get(position);
				
			    String NATURE = "";
				 if(count<result_size){
				    if( stock.getNature() == -1) {
				    	NATURE = " Starting inventory";
				    	start_inv_qty = start_inv_qty + stock.getQty();
				    }else if( stock.getNature() == 1 || stock.getNature() == 2 ){
				    	NATURE = " Delivery ";
				    	del_qty = del_qty + stock.getQty();
				    }else if( stock.getNature() == 4 || stock.getNature() == 5 ){
				    	NATURE = " Purchase ";
				    	pur_qty = pur_qty + stock.getQty();
				    }
				 }
			    total_val.setText(String.valueOf((start_inv_qty +pur_qty )-(del_qty)));
		
			    holder.name.setText(NATURE);
			    holder.measurement.setText(String.valueOf(stock.getQty()));
			    count++;
			    Log.e("size",String.valueOf(count));
		    } else {
		    	
		    	holder = (UserHolder) row.getTag();
		    	 
		    	
		    }
		    
		    
		    return row;
		}
	
		
    }

    class UserHolder {
	    TextView name;
	    TextView measurement;
	 
	}	
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
