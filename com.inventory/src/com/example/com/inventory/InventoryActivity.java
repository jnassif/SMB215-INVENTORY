package com.example.com.inventory;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class InventoryActivity extends Activity{
	Button add_btn;
    ListView inventory_listview;
    ArrayList<Inventory> inventory_data = new ArrayList<Inventory>();
    Inentory_Adapter cAdapter;
    DatabaseHandler db;
    String Toast_msg;
    String meas;
    String nature;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.product_activity);
	nature =  PreferenceManager.getDefaultSharedPreferences(InventoryActivity.this).getString("condition", "defaultStringIfNothingFound");
	try {
		inventory_listview = (ListView) findViewById(R.id.list);
		inventory_listview.setItemsCanFocus(false);
	    add_btn = (Button) findViewById(R.id.add_btn);

	    Set_Referash_Data();

	} catch (Exception e) {
	    // TODO: handle exception
	    Log.e("some error", "" + e);
	}
	add_btn.setOnClickListener(new View.OnClickListener() {

		//@Override
	    public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent add_user = new Intent(InventoryActivity.this,Add_Update_Inventory.class);
		add_user.putExtra("called", "add");
		
		add_user.putExtra("inv_nature", nature);
		
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
    	inventory_data.clear();
		db = new DatabaseHandler(this);
	
		ArrayList<Inventory> Inventory_array_from_db = db.Get_inventories(Integer.parseInt(nature));
	
		for (int i = 0; i < Inventory_array_from_db.size(); i++) {
	
		    int inventory =     Inventory_array_from_db.get(i).get_inventory();
		    int client  =     Inventory_array_from_db.get(i).get_client();
		    int measurement = Inventory_array_from_db.get(i).get_nature();
		    int paymode    = Inventory_array_from_db.get(i).get_paymode();
		    int warehouse = Inventory_array_from_db.get(i).get_warehouse();
		    String date = Inventory_array_from_db.get(i).get_theDate();
		    
		    Inventory cnt = new Inventory();
		    cnt.set_inventory(inventory);
		    cnt.set_client(client);
		    cnt.set_nature(measurement);
		    cnt.set_paymode(paymode);
		    cnt.set_theDate(date);
		    cnt.set_warehouse(warehouse);
		    
	
		    inventory_data.add(cnt);
		}
		db.close();
		cAdapter = new Inentory_Adapter(InventoryActivity.this, R.layout.listview_inventory,inventory_data);
		inventory_listview.setAdapter(cAdapter);
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
    
    //********************* Populate all the inventories in the list *********************
    public class Inentory_Adapter extends ArrayAdapter<Inventory> {
		Activity activity;
		int layoutResourceId;
		Inventory inventory;
		ArrayList<Inventory> data = new ArrayList<Inventory>();

		public Inentory_Adapter(Activity act, int layoutResourceId,ArrayList<Inventory> data) {
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
				holder.client = (TextView) row.findViewById(R.id.client_txt);
				holder.nature = (TextView) row.findViewById(R.id.nature_txt);
				holder.paymode = (TextView) row.findViewById(R.id.paymode);
				holder.thedate = (TextView) row.findViewById(R.id.date_txt);
				holder.warehouse = (TextView) row.findViewById(R.id.warehouse_txt);
				holder.edit = (Button) row.findViewById(R.id.btn_update);
				holder.delete = (Button) row.findViewById(R.id.btn_delete);
				row.setTag(holder);
		    } else {
		    	holder = (UserHolder) row.getTag();
		    }
		    
		    inventory = data.get(position);
		   	//set client
		    Client client= db.Get_client(inventory.get_client());
		   	Warehouse war = db.Get_warehouse(inventory.get_warehouse());
		   	holder.edit.setTag(inventory.get_inventory());
		    holder.delete.setTag(inventory.get_inventory());
		    holder.client.setText(client.getName());
		    
		    String pay_mode ="";
		    switch(inventory.get_paymode()){
			    case 0 : pay_mode = "check";break ;
			    case 1 : pay_mode = "Cash";break ;
			    case 2 : pay_mode = "Visa";break ;
			}
		    
		    //holder.nature.setText(String.valueOf(inventory.get_nature()));
		    holder.paymode.setText(pay_mode);
		    holder.thedate.setText(inventory.get_theDate());
		    holder.warehouse.setText(war.get_name());
		    
		   
		    
		    holder.edit.setOnClickListener(new OnClickListener() {
		    	@Override
				public void onClick(View v) {
				    // TODO Auto-generated method stub
				    Log.i("Edit Button Clicked", "**********");
	
				    Intent update_user = new Intent(activity,Add_Update_Inventory.class);
				    update_user.putExtra("called", "update");
				    update_user.putExtra("inv_nature",nature );
				    update_user.putExtra("INVENTORY_ID", v.getTag().toString());
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
				    adb.setMessage("Are you sure you want to delete");
				    final int inventory_id = Integer.parseInt(v.getTag().toString());
				    adb.setNegativeButton("Cancel", null);
				    adb.setPositiveButton("Ok",
					    new AlertDialog.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog,
							int which) {
						    // MyDataObject.remove(positionToRemove);
						    DatabaseHandler dBHandler = new DatabaseHandler(activity.getApplicationContext());
						    dBHandler.Delete_inventory(inventory_id);
						    InventoryActivity.this.onResume();
	
						}
					    });
				    adb.show();
				}

		    });
		    return row;

		}

		class UserHolder {
		    TextView client;
		    TextView nature;
		    TextView thedate;
		    TextView warehouse;
		    TextView paymode;
		   
		    Button edit;
		    Button delete;
		}

    }
}
