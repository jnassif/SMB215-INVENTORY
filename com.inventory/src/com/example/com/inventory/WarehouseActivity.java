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
import android.widget.TextView;
import android.widget.Toast;

public class WarehouseActivity extends Activity {

	Button add_btn;
    ListView Warehouse_listview;
    ArrayList<Warehouse> Warehouse_data = new ArrayList<Warehouse>();
    Warehouse_Adapter cAdapter;
    DatabaseHandler db;
    String Toast_msg;

    @Override
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.warehouse_activity);
	try {
	    Warehouse_listview = (ListView) findViewById(R.id.list);
	    Warehouse_listview.setItemsCanFocus(false);
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
		Intent add_user = new Intent(WarehouseActivity.this,Add_Update_User.class);
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
		Warehouse_data.clear();
		db = new DatabaseHandler(this);
		ArrayList<Warehouse> Warehouse_array_from_db = db.Get_Warehouses();
	
		for (int i = 0; i < Warehouse_array_from_db.size(); i++) {
	
		    int warehouse =     Warehouse_array_from_db.get(i).get_warehouse();
		    String name =   Warehouse_array_from_db.get(i).get_name();
		    String address = Warehouse_array_from_db.get(i).get_address();
		    
		    Warehouse cnt = new Warehouse();
		    cnt.set_warehouse(warehouse);
		    cnt.set_name(name);
		    cnt.set_address(address);
		    
	
		    Warehouse_data.add(cnt);
		}
		db.close();
		cAdapter = new Warehouse_Adapter(WarehouseActivity.this, R.layout.listview_row,Warehouse_data);
		Warehouse_listview.setAdapter(cAdapter);
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
    public class Warehouse_Adapter extends ArrayAdapter<Warehouse> {
		Activity activity;
		int layoutResourceId;
		Warehouse user;
		ArrayList<Warehouse> data = new ArrayList<Warehouse>();

		public Warehouse_Adapter(Activity act, int layoutResourceId,ArrayList<Warehouse> data) {
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
				holder.address = (TextView) row.findViewById(R.id.user_email_txt);
				holder.edit = (Button) row.findViewById(R.id.btn_update);
				holder.delete = (Button) row.findViewById(R.id.btn_delete);
				row.setTag(holder);
		    } else {
		    	holder = (UserHolder) row.getTag();
		    }
		    user = data.get(position);
		    holder.edit.setTag(user.get_warehouse());
		    holder.delete.setTag(user.get_warehouse());
		    holder.name.setText(user.get_name());
		    holder.address.setText(user.get_address());
		    

		    holder.edit.setOnClickListener(new OnClickListener() {
		    	@Override
				public void onClick(View v) {
				    // TODO Auto-generated method stub
				    Log.i("Edit Button Clicked", "**********");
	
				    Intent update_user = new Intent(activity,Add_Update_User.class);
				    update_user.putExtra("called", "update");
				    update_user.putExtra("USER_ID", v.getTag().toString());
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
				    final int user_id = Integer.parseInt(v.getTag().toString());
				    adb.setNegativeButton("Cancel", null);
				    adb.setPositiveButton("Ok",
					    new AlertDialog.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog,
							int which) {
						    // MyDataObject.remove(positionToRemove);
						    DatabaseHandler dBHandler = new DatabaseHandler(activity.getApplicationContext());
						    dBHandler.Delete_warehouse(user_id);
						    WarehouseActivity.this.onResume();
	
						}
					    });
				    adb.show();
				}

		    });
		    return row;

		}

		class UserHolder {
		    TextView name;
		    TextView address;
		    Button edit;
		    Button delete;
		    
		}

    }

   
	
}
