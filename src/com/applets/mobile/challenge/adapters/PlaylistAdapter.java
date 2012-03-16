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

public class PlaylistAdapter extends BaseAdapter {

    private class PlaylistWrapper {

	private View view;
	private TextView label;
	private ImageView image;

	public PlaylistWrapper(View view) {
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
	    getImage().setImageResource(R.drawable.list);
	}
    }

    private Context ctx;
    private JSONObject json;
    private JSONArray folders;
    private JSONArray images;
    private JSONArray files;

    public PlaylistAdapter(Context ctx, JSONObject array) {
	this.ctx = ctx;
	this.json = array;

	try {
	    this.folders = json.getJSONArray("playlist");
	} catch (JSONException e) {
	    e.printStackTrace();
	}
    }

    @Override
    public int getCount() {
	return folders.length();
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
	PlaylistWrapper wrapper;
	View v = convertView;
	if (v == null) {
	    // inflate the xml row layout from context
	    v = LayoutInflater.from(ctx).inflate(R.layout.basic_row, null);
	    wrapper = new PlaylistWrapper(v);
	    v.setTag(wrapper);
	} else {
	    wrapper = (PlaylistWrapper) v.getTag();
	}
	wrapper.setLabel(getLabel(position));
	wrapper.setImage();
	return v;
    }

    public String getLabel(int position) {
	String lbl = "";
	try {

	    if (position < folders.length()) {
		lbl = folders.getString(position);
		lbl.substring(0, lbl.indexOf(".xml"));
	    }
	} catch (JSONException e) {
	    e.printStackTrace();
	}
	return lbl;
    }
}
