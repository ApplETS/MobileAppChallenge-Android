package com.applets.mobile.challenge.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.applets.mobile.challenge.R;

public class GridIconAdapter extends BaseAdapter {

    // the wrapper contains the ui info
    public class IconWrapper {

	private TextView label;
	private View v;
	private ImageView image;

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

	public void setImage(int resid) {
	    getImage().setImageResource(resid);
	}

	private ImageView getImage() {
	    if (image == null) {
		image = (ImageView) v.findViewById(R.id.imageView1);
	    }
	    return image;
	}
    }

    // list of button labels
    private String[] labels;
    private static final int[] icons = new int[] { R.drawable.mic,
	    R.drawable.cd, R.drawable.music, R.drawable.music, R.drawable.list };
    private Context ctx;

    public GridIconAdapter(Context ctx) {
	this.ctx = ctx;
	labels = ctx.getResources().getStringArray(R.array.icon_labels);
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
	    // inflate the xml layout from context
	    v = LayoutInflater.from(ctx).inflate(R.layout.dashboard_icon, null);
	    wrapper = new IconWrapper(v);
	    v.setTag(wrapper);
	} else {
	    wrapper = (IconWrapper) v.getTag();
	}
	wrapper.setLabel(labels[position]);
	wrapper.setImage(icons[position]);

	return v;
    }

}
