package com.applets.mobile.challenge;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.applets.mobile.challenge.adapters.GridIconAdapter;

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
	// try login
	String h = preferences.getString("host", TOKEN);
	String u = preferences.getString("username", TOKEN);
	String p = preferences.getString("password", TOKEN);

	if (u.equalsIgnoreCase(TOKEN) || p.equalsIgnoreCase(TOKEN)
		|| h.equalsIgnoreCase(TOKEN)) {
	    showAlert();
	}
	/*
	 * TODO crash puisque le json retourne null pointer exception en
	 * commentaire pour l'instant new JSONRetreiver(new IAsyncTaskListener()
	 * {
	 * 
	 * @Override public void onPostExecute(JSONObject result) { try { TOKEN
	 * = result.getString("token"); } catch (JSONException e) {
	 * e.printStackTrace(); } } }).execute(h + "/login?username=" + u +
	 * "&password=" + p);
	 */
    }

    private void showAlert() {
	AlertDialog.Builder builder = new AlertDialog.Builder(this);
	builder.setMessage(getString(R.string.hello))
		.setCancelable(false)
		.setPositiveButton("Yes",
			new DialogInterface.OnClickListener() {
			    public void onClick(DialogInterface dialog, int id) {
				startActivity(new Intent(
					MobileAppChallengeActivity.this,
					PreferencesActivity.class));
			    }
			})
		.setNegativeButton("No", new DialogInterface.OnClickListener() {
		    public void onClick(DialogInterface dialog, int id) {
			dialog.cancel();
		    }
		});
	AlertDialog alert = builder.create();
    }

    @Override
    public void onItemClick(AdapterView<?> adapter, View view, int position,
	    long id) {
	Intent intent = null;
	switch (position) {
	case 1:
	    intent = new Intent(this, BasicListActivity.class);
	    intent.putExtra("type", "listalbums");
	    break;
	case 2:
	    intent = new Intent(this, BasicListActivity.class);
	    intent.putExtra("type", "playlist");
	    break;
	default:
	    intent = new Intent(this, BasicListActivity.class);
	    intent.putExtra("type", "artist");
	    break;
	}
	startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
	this.menu = menu;
	new MenuInflater(getApplication()).inflate(R.menu.menu, this.menu);
	menu.getItem(1).setEnabled(false);
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