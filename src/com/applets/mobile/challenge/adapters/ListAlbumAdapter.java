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

public class ListAlbumAdapter extends BaseAdapter {

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

	public void setArtistLabel(String string) {
	    getArtistLabel().setText(string);
	}

	private TextView getAlbumLabel() {
	    if (label == null) {
		label = (TextView) view.findViewById(R.id.album_row_title);
	    }
	    return label;
	}

	private TextView getArtistLabel() {
	    if (artist_label == null) {
		artist_label = (TextView) view
			.findViewById(R.id.album_artist_lbl);
	    }
	    return artist_label;
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

    public ListAlbumAdapter(Context ctx, JSONObject array) {
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
	    v = LayoutInflater.from(ctx).inflate(R.layout.album_row, null);
	    wrapper = new AlbumWrapper(v);
	    v.setTag(wrapper);
	} else {
	    wrapper = (AlbumWrapper) v.getTag();
	}
	wrapper.setAlbumLabel(getAlbumLabel(position));
	wrapper.setArtistLabel(getArtistLabel(position));
	wrapper.setImage();
	return v;
    }

    private String getAlbumLabel(int position) {
	String album = "";
	try {
	    JSONObject o = albums.getJSONObject(position);
	    album = o.getString("album");
	} catch (JSONException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	return album;
    }

    private String getArtistLabel(int position) {
	String artist = "";
	try {
	    JSONObject o = albums.getJSONObject(position);
	    artist = o.getString("artist");
	} catch (JSONException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	return artist;
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
