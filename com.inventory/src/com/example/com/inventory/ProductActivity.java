package com.example.com.inventory;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class ProductActivity extends Activity{
	Button add_btn;
    ListView product_listview;
    ArrayList<Product> product_data = new ArrayList<Product>();
    Product_Adapter cAdapter;
    DatabaseHandler db;
    String Toast_msg;
    String meas;

    
    @Override
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.product_activity);
	try {
		product_listview = (ListView) findViewById(R.id.list);
		product_listview.setItemsCanFocus(false);
	    add_btn = (Button) findViewById(R.id.add_btn);

	    Set_Referash_Data();

	} catch (Exception e) {
	    // TODO: handle exception
	    Log.e("some error", "" + e);
	}
	add_btn.setOnClickListener(new View.OnClickListener() {

	//    @Override
	    public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent add_user = new Intent(ProductActivity.this,Add_Update_Product.class);
		add_user.putExtra("called", "add");
		add_user.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(add_user);
		finish();
	    }
	});

    }

   // public void Call_My_Blog(View v) {
	//Intent intent = new Intent(Main_Screen.this, My_Blog.class);
	//startActivity(intent);

  //  }

    public void Set_Referash_Data() {
    	// remove all the elements from the array 
    	product_data.clear();
		db = new DatabaseHandler(this);
		ArrayList<Product> Product_array_from_db = db.Get_Products();
	
		for (int i = 0; i < Product_array_from_db.size(); i++) {
	
		    int product =     Product_array_from_db.get(i).get_product();
		    String name =     Product_array_from_db.get(i).get_name();
		    int measurement = Product_array_from_db.get(i).get_measurement();
		    Float price     = Product_array_from_db.get(i).get_price();
		    
		    Product cnt = new Product();
		    cnt.set_product(product);
		    cnt.set_name(name);
		    cnt.set_measurement(measurement);
		    cnt.set_price(price);
		    
	
		    product_data.add(cnt);
		}
		db.close();
		cAdapter = new Product_Adapter(ProductActivity.this, R.layout.listview_row_prod,product_data);
		product_listview.setAdapter(cAdapter);
		cAdapter.notifyDataSetChanged();
    }

    public void Show_Toast(String msg) {
	Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onResume() {
	// TODO Auto-generated method stub
	super.onResume();
	Set_Referash_Data();

    }
    
    //********************* Populate all the warehouses in the list *********************
    public class Product_Adapter extends ArrayAdapter<Product> {
		Activity activity;
		int layoutResourceId;
		Product product;
		ArrayList<Product> data = new ArrayList<Product>();

		public Product_Adapter(Activity act, int layoutResourceId,ArrayList<Product> data) {
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
		    Log.e("asd", String.valueOf(product.get_product()));
		    holder.edit.setTag(product.get_product());
		    holder.delete.setTag(product.get_product());
		    holder.name.setText(product.get_name());
		    product.get_measurement();
		    
		    switch(product.get_measurement()){
		    	case 0 : meas = "Unit"; break; 
		    	case 1 :meas = "Kg";break;
		    	case 2 :meas = "L";break;
		    }
		    holder.measurement.setText(meas);
		    Float price = product.get_price();
		    holder.product_price.setText(price.toString());

		    holder.edit.setOnClickListener(new OnClickListener() {
		    	@Override
				public void onClick(View v) {
				    // TODO Auto-generated method stub
				    Log.i("Edit Button Clicked", "**********");
	
				    Intent update_user = new Intent(activity,Add_Update_Product.class);
				    update_user.putExtra("called", "update");
				    update_user.putExtra("PRODUCT_ID", v.getTag().toString());
				    activity.startActivity(update_user);
	
				}
		    });
		    
		    holder.delete.setOnClickListener(new OnClickListener() {
			    @Override
				public void onClick(final View v) {
				    // TODO Auto-generated method stub
	
				    // show a message while loader is loading
	
				    AlertDialog.Builder adb = new AlertDialog.Builder(activity);
				    adb.setTitle("Delete?");
				    adb.setMessage("Are you sure you want to delete ");
				    final int product_id = Integer.parseInt(v.getTag().toString());
				    adb.setNegativeButton("Cancel", null);
				    adb.setPositiveButton("Ok",
					    new AlertDialog.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog,
							int which) {
						    // MyDataObject.remove(positionToRemove);
						    DatabaseHandler dBHandler = new DatabaseHandler(activity.getApplicationContext());
						    dBHandler.Delete_product(product_id);
						    ProductActivity.this.onResume();
	
						}
					    });
				    adb.show();
				}

		    });
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
}
