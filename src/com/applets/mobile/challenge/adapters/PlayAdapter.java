package com.applets.mobile.challenge.adapters;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

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
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean isPlaying() {
		return playing;
	}
	
}
