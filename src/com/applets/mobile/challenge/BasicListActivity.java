package com.applets.mobile.challenge;

import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.applets.mobile.challenge.adapters.ArtistAdapter;
import com.applets.mobile.challenge.utils.AdapterFactory;
import com.applets.mobile.challenge.utils.IAsyncTaskListener;
import com.applets.mobile.challenge.utils.JSONRetreiver;

public class BasicListActivity extends ListActivity implements
	IAsyncTaskListener {
    private String query;
    private String type;
    private String path = "";
    // Handles the onPostExecute
    private final Handler handler = new Handler();
    private IAsyncTaskListener activity = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.basic_list);

	Bundle bundle = getIntent().getExtras();
	query = bundle.getString("query");
	type = bundle.getString("type");

	path = bundle.getString("path");

	if (path == null) {
	    path = "";
	}

	if (type.equals("songs") || type.equals("albums")) {
	    new JSONRetreiver(this).execute("http://highwizard.com:8080/list",
		    query);
	} else if (type.equals("listalbums")) {
	    new JSONRetreiver(this)
		    .execute("http://highwizard.com:8080/list/albums");
	} else if (type.equals("artist")) {
	    new JSONRetreiver(this).execute("http://highwizard.com:8080/list");
	} else if(type.equals("playlist")){
	    new JSONRetreiver(this).execute("http://highwizard.com:8080/playlist");
	}

	this.getListView().setOnItemLongClickListener(new LongClickListener());

    }

    private class LongClickListener implements OnItemLongClickListener {

	@Override
	public boolean onItemLongClick(final AdapterView<?> adapterViews,
		View view, final int position, long id) {

	    if (type.equals("songs")) {
		final CharSequence[] items = { "Add song to playlist",
			"Play now" };

		AlertDialog.Builder builder = new AlertDialog.Builder(
			view.getContext());
		builder.setTitle("Choose an option");
		builder.setItems(items, new DialogInterface.OnClickListener() {
		    public void onClick(DialogInterface dialog, int item) {
			if (item == 0) {

			} else if (item == 1) {

			    String text = ((ArtistAdapter) adapterViews
				    .getAdapter()).getLabel(position);

			    Intent intent = null;
			    intent = new Intent((Context) activity,
				    MediaControllerActivity.class);
			    intent.putExtra("file", text);
			    startActivity(intent);
			}
		    }
		});
		AlertDialog alert = builder.create();
		alert.show();
	    }
	    return false;
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
    protected void onListItemClick(ListView listView, View view, int position,
	    long id) {
	String text = (String) ((BaseAdapter) listView.getAdapter())
		.getItem(position);
	Intent intent = null;
	if (type.equals("artist")) {
	    intent = new Intent(this, BasicListActivity.class);
	    intent.putExtra("type", "albums");
	    intent.putExtra("query", "folder_1=" + text + "/");
	    intent.putExtra("path", path + text + "/");
	} else if (type.equals("albums")) {
	    intent = new Intent(this, BasicListActivity.class);
	    intent.putExtra("type", "songs");
	    intent.putExtra("query", query + text);
	    intent.putExtra("path", path + text + "/");
	} else if (type.equals("songs")) {
	    intent = new Intent((Context) activity,
		    MediaControllerActivity.class);
	    intent.putExtra("file", path + text);
	} else if(type.equals("listalbums")){
	    intent = new Intent(this, BasicListActivity.class);
	    intent.putExtra("type", "songs");
	    intent.putExtra("query", "folder_1=" + text + "/");
	}else {
	    intent = new Intent(this, BasicListActivity.class);
	    intent.putExtra("type", "listalbums");
	    return;
	}

	startActivity(intent);
    }
}
