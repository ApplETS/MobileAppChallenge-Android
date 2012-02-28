package com.applets.mobile.challenge.adapters;

import org.json.JSONArray;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.applets.mobile.challenge.R;

public class ArtistAdapter extends BaseAdapter {

	public class ArtistWrapper {

		public ArtistWrapper(View v) {
			// TODO Auto-generated constructor stub
		}

		public void setLabel(String string) {
			// TODO Auto-generated method stub

		}

	}

	private Context ctx;
	private JSONArray json;

	public ArtistAdapter(Context ctx, JSONArray array) {
		this.ctx = ctx;
		this.json = array;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ArtistWrapper wrapper;
		View v = convertView;
		if (v == null) {
			// inflate the xml layout from context
			v = LayoutInflater.from(ctx).inflate(R.layout.basic_list, null);
			wrapper = new ArtistWrapper(v);
			v.setTag(wrapper);
		} else {
			wrapper = (ArtistWrapper) v.getTag();
		}
		wrapper.setLabel("AAA");

		return v;
	}
}
