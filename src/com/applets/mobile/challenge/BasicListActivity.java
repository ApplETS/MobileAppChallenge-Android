package com.applets.mobile.challenge;

import org.json.JSONObject;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ListView;

import com.applets.mobile.challenge.adapters.ArtistAdapter;
import com.applets.mobile.challenge.utils.AdapterFactory;
import com.applets.mobile.challenge.utils.IAsyncTaskListener;
import com.applets.mobile.challenge.utils.JSONRetreiver;

//TODO on long click for songs
public class BasicListActivity extends ListActivity implements
	IAsyncTaskListener {

    private String query;
    private String type;
    // Handles the onPostExecute
    private final Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.basic_list);

		Bundle bundle = getIntent().getExtras();
		query = bundle.getString("query");
		type = bundle.getString("type");
		
		if (type.equals("songs")) {
			new JSONRetreiver(this).execute("http://highwizard.com:8080/list", query);
		} else if (type.equals("albums")) {
			new JSONRetreiver(this).execute("http://highwizard.com:8080/list", query);
		} else if (type.equals("artist")){
			new JSONRetreiver(this).execute("http://highwizard.com:8080/list");
		}
		
	}

    @Override
    public void onPostExecute(final JSONObject result) {
	final Context ctx = this;
	handler.post(new Runnable() {

	    @Override
	    public void run() {
		setListAdapter(AdapterFactory.getInstance().getAdapter(type,
			ctx, result));
	    }
	});
    }

	@Override
	protected void onListItemClick(ListView listView, View view, int position, long id) {
		String text = ((ArtistAdapter) listView.getAdapter()).getLabel(position);
		Intent intent = null;
		if (type.equals("artist")) {
			intent = new Intent(this, BasicListActivity.class);
			intent.putExtra("type", "albums");
			intent.putExtra("query", "folder_1=" + text + "/");
		} else if (type.equals("albums")) {
			intent = new Intent(this, BasicListActivity.class);
			intent.putExtra("type", "songs");
			intent.putExtra("query", query + text);
		} else {
			//TODO if for songs
			return;
		}
		
		startActivity(intent);
    }
}
