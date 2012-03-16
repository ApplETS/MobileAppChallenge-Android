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

public class PlayAdapter {

	private Context ctx;
	private JSONObject json;
	private boolean playing;
	private String file;

	public PlayAdapter(Context ctx, JSONObject array) {
		this.ctx = ctx;
		this.json = array;
		
		try {
			String message = json.getString("message");
			if (message.equals("null")) {
				playing = false;
			} else {
				playing = true;
				file = message;
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public boolean isPlaying() {
		return playing;
	}
	
}
