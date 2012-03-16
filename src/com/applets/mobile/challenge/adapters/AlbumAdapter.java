package com.applets.mobile.challenge.adapters;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.applets.mobile.challenge.R;

public class AlbumAdapter extends BaseAdapter {

    private class AlbumWrapper {

	private View view;
	private TextView label;
	private ImageView image;

	public AlbumWrapper(View view) {
	    this.view = view;
	}

	public void setLabel(String string) {
	    getLabel().setText(string);
	}

	private TextView getLabel() {
	    if (label == null) {
		label = (TextView) view.findViewById(R.id.base_row_title);
	    }
	    return label;
	}

	private ImageView getImage() {
	    if (image == null) {
		image = (ImageView) view.findViewById(R.id.base_row_image);
	    }
	    return image;
	}

	public void setImage() {
	    getImage().setImageResource(R.drawable.cd);
	}
    }

    private Context ctx;
    private JSONObject json;
    private JSONArray albums;

    public AlbumAdapter(Context ctx, JSONObject array) {
	this.ctx = ctx;
	this.json = array;

	try {
	    this.albums = json.getJSONArray("albums");
	} catch (JSONException e) {
	    e.printStackTrace();
	}
    }

    @Override
    public int getCount() {
	return albums.length();
    }

    @Override
    public Object getItem(int arg0) {
	return getLabel(arg0);
    }

    @Override
    public long getItemId(int arg0) {
	// TODO Auto-generated method stub
	return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
	AlbumWrapper wrapper;
	View v = convertView;
	if (v == null) {
	    // inflate the xml row layout from context
	    v = LayoutInflater.from(ctx).inflate(R.layout.basic_row, null);
	    wrapper = new AlbumWrapper(v);
	    v.setTag(wrapper);
	} else {
	    wrapper = (AlbumWrapper) v.getTag();
	}
	wrapper.setLabel(getLabel(position));
	wrapper.setImage();
	return v;
    }

    public String getLabel(int position) {
	String lbl = "";
	try {

	    if (position < albums.length()) {
		lbl = albums.getString(position);
	    }
	} catch (JSONException e) {
	    e.printStackTrace();
	}
	return lbl;
    }
}
