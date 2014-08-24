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


public class ClientActivity extends Activity{
	Button add_btn;
    ListView Client_listview;
    ArrayList<Client> client_data = new ArrayList<Client>();
    Client_Adapter cAdapter;
    DatabaseHandler db;
    String Toast_msg;

    @Override
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.client_activity);
	try {
		Client_listview = (ListView) findViewById(R.id.list);
		Client_listview.setItemsCanFocus(false);
	    add_btn = (Button) findViewById(R.id.add_btn);

	    Set_Referash_Data();

	} catch (Exception e) {
	    // TODO: handle exception
	    Log.e("some error", "" + e);
	}
	add_btn.setOnClickListener(new View.OnClickListener() {

	    @Override
	    public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent add_user = new Intent(ClientActivity.this,
				Add_Update_Client.class);
		add_user.putExtra("called", "add");
		add_user.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
			| Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(add_user);
		finish();
	    }
	});

    }

 
    public void Set_Referash_Data() {
    client_data.clear();
	db = new DatabaseHandler(this);
	ArrayList<Client> client_array_from_db = db.Get_clients();

	for (int i = 0; i < client_array_from_db.size(); i++) {

	    int tidno = client_array_from_db.get(i).getClient();
	    String name = client_array_from_db.get(i).getName();
	    String address = client_array_from_db.get(i).getAddress();
	    String notes = client_array_from_db.get(i).getNotes();
	    Client cnt = new Client();
	    cnt.setClient(tidno);
	    cnt.setName(name);
	    cnt.setAddress(address);
	    cnt.setNotes(notes);

	    client_data.add(cnt);
	}
	db.close();
	cAdapter = new Client_Adapter(ClientActivity.this, R.layout.listview_row,
			client_data);
	Client_listview.setAdapter(cAdapter);
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

    public class Client_Adapter extends ArrayAdapter<Client> {
	Activity activity;
	int layoutResourceId;
	Client user;
	ArrayList<Client> data = new ArrayList<Client>();

	public Client_Adapter(Activity act, int layoutResourceId,
		ArrayList<Client> data) {
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

	    if (row == null) {
		LayoutInflater inflater = LayoutInflater.from(activity);

		row = inflater.inflate(layoutResourceId, parent, false);
		holder = new UserHolder();
		holder.name = (TextView) row.findViewById(R.id.user_name_txt);
		holder.address = (TextView) row.findViewById(R.id.client_address);
		holder.notes = (TextView) row.findViewById(R.id.client_notes);
		holder.edit = (Button) row.findViewById(R.id.btn_update);
		holder.delete = (Button) row.findViewById(R.id.btn_delete);
		row.setTag(holder);
	    } else {
		holder = (UserHolder) row.getTag();
	    }
	    user = data.get(position);
	    holder.edit.setTag(user.getClient());
	    holder.delete.setTag(user.getClient());
	    holder.name.setText(user.getName());
	    holder.address.setText(user.getAddress());
	    holder.notes.setText(user.getNotes());

	    holder.edit.setOnClickListener(new OnClickListener() {

		@Override
		public void onClick(View v) {
		    // TODO Auto-generated method stub
		    Log.i("Edit Button Clicked", "**********");

		    Intent update_user = new Intent(activity,
		    		Add_Update_Client.class);
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
				    DatabaseHandler dBHandler = new DatabaseHandler(
					    activity.getApplicationContext());
				    dBHandler.Delete_client(user_id);
				    ClientActivity.this.onResume();

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
	    TextView notes;
	    Button edit;
	    Button delete;
	}

    }
}
