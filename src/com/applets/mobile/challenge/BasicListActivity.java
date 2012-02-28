package com.applets.mobile.challenge;

import org.json.JSONArray;

import android.app.ListActivity;
import android.os.Bundle;

import com.applets.mobile.challenge.utils.AdapterFactory;
import com.applets.mobile.challenge.utils.IAsyncTaskListener;
import com.applets.mobile.challenge.utils.JSONRetreiver;

public class BasicListActivity extends ListActivity implements
		IAsyncTaskListener {

	private String query;
	private String type;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		Bundle b = getIntent().getExtras();
		query = b.getString("type");
		type = b.getString("type");
		new JSONRetreiver(this).execute("http://192.168.1.3:8080/" + query);

	}

	@Override
	public void onPostExecute() {
		setContentView(R.layout.basic_list);
		setListAdapter(AdapterFactory.getInstance().getAdapter(type,this,
				new JSONArray()));
	}

}
