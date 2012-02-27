package com.applets.mobile.challenge.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.applets.mobile.challenge.R;

public class GridIconAdapter extends BaseAdapter {

    public class IconWrapper {

	private TextView label;
	private View v;

	public IconWrapper(View v) {
	    this.v = v;
	}

	public TextView getLabel() {
	    if (label == null) {
		label = (TextView) v.findViewById(R.id.textView1);
	    }
	    return label;
	}

	public void setLabel(String text) {
	    getLabel().setText(text);
	}
    }

    private static final String[] labels = new String[] { "Artist", "Albums",
	    "Genre", "Songs", "Playlist" };
    private Context ctx;

    public GridIconAdapter(Context ctx) {
	this.ctx = ctx;

    }

    @Override
    public int getCount() {
	return labels.length;
    }

    @Override
    public Object getItem(int position) {
	return null;
    }

    @Override
    public long getItemId(int position) {
	return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
	IconWrapper wrapper;
	View v = convertView;
	if (v == null) {
	    v = LayoutInflater.from(ctx).inflate(R.layout.dashboard_icon, null);
	    wrapper = new IconWrapper(v);
	    v.setTag(wrapper);
	} else {
	    wrapper = (IconWrapper) v.getTag();
	}
	wrapper.setLabel(labels[position]);

	return v;
    }

}
