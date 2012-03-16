package com.applets.mobile.challenge.adapters;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.applets.mobile.challenge.R;

public class PlayAdapter   {

	private Context ctx;
	private JSONObject json;

	public PlayAdapter(Context ctx, JSONObject array) {
		this.ctx = ctx;
		this.json = array;

		
	}

}
