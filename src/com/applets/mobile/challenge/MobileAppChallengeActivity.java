package com.applets.mobile.challenge;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.applets.mobile.challenge.adapters.GridIconAdapter;

public class MobileAppChallengeActivity extends Activity implements
		OnItemClickListener {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		GridView g = (GridView) findViewById(R.id.gridView1);
		g.setAdapter(new GridIconAdapter(this));
		g.setOnItemClickListener(this);
	}

	@Override
	public void onItemClick(AdapterView<?> adapter, View view, int position,
			long id) {
		Intent intent = null;

		switch (position) {

		case GridIconAdapter.ARTIST:
			intent = new Intent(this, BasicListActivity.class);
			intent.putExtra("type", "artist");
			intent.putExtra("query", "all");
			break;

		case GridIconAdapter.ALBUM:
			intent = new Intent(this, BasicListActivity.class);
			intent.putExtra("type", "albums");
			intent.putExtra("query", "all");
			break;

		default:
			intent = new Intent(this, BasicListActivity.class);
			intent.putExtra("type", "artist");
			intent.putExtra("query", "all");
			break;
		}
		startActivity(intent);
	}
}