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
	private TextView artist_label;

	public AlbumWrapper(View view) {
	    this.view = view;
	}

	public void setAlbumLabel(String string) {
	    getAlbumLabel().setText(string);
	}

	private TextView getAlbumLabel() {
	    if (label == null) {
		label = (TextView) view.findViewById(R.id.album_row_title);
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
    private JSONArray folders;
    private JSONArray images;
    private JSONArray files;

    public AlbumAdapter(Context ctx, JSONObject array) {
	this.ctx = ctx;
	this.json = array;

	try {
	    this.folders = json.getJSONArray("folders");
	    this.files = json.getJSONArray("files");
	    this.images = json.getJSONArray("images");
	} catch (JSONException e) {
	    e.printStackTrace();
	}
    }

    @Override
    public int getCount() {
	return files.length() + images.length() + folders.length();
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
	wrapper.setAlbumLabel(getLabel(position));
	wrapper.setImage();
	return v;
    }

    public String getLabel(int position) {
	String lbl = "";
	try {

	    if (position < folders.length()) {
		lbl = folders.getString(position);
	    } else if (position < files.length()) {
		lbl = files.getString(position);
	    }
	} catch (JSONException e) {
	    e.printStackTrace();
	}
	return lbl;
    }
}
