package com.applets.mobile.challenge;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.applets.mobile.challenge.adapters.GridIconAdapter;
import com.applets.mobile.challenge.utils.IAsyncTaskListener;
import com.applets.mobile.challenge.utils.JSONRetreiver;

public class MobileAppChallengeActivity extends Activity implements
	OnItemClickListener {

    private Menu menu;
    private SharedPreferences preferences;
    private String TOKEN = "NONE";
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.main);

	GridView g = (GridView) findViewById(R.id.gridView1);
	g.setAdapter(new GridIconAdapter(this));
	g.setOnItemClickListener(this);
	
	// Initialize preferences
	preferences = PreferenceManager.getDefaultSharedPreferences(this);
	//try login
	String u = preferences.getString("username", "NONE");
	String p = preferences.getString("password", "NONE");
	
	new JSONRetreiver(new IAsyncTaskListener() {
	    
	    @Override
	    public void onPostExecute(JSONObject result) {
		try {
		    TOKEN = result.getString("token");	
		} catch (JSONException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		}
	    }
	}).execute(getString(R.string.host)+"/login?username="+u+"&password="+p);

    }

    @Override
    public void onItemClick(AdapterView<?> adapter, View view, int position,
	    long id) {
	Intent intent = null;
	switch (position) {
	default:
	    intent = new Intent(this, BasicListActivity.class);
	    intent.putExtra("type", "artist");
	    intent.putExtra("query", "all");
	    break;
	}
	startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
	this.menu = menu;
	new MenuInflater(getApplication()).inflate(R.menu.menu, this.menu);
	return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
	switch (item.getItemId()) {
	case R.id.menu_pref:
	    startActivity(new Intent(this, PreferencesActivity.class));
	    break;
	}
	return super.onOptionsItemSelected(item);
    }
}