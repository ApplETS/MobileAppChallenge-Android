package com.applets.mobile.challenge.adapters;

import org.json.JSONObject;

import android.content.Context;

public class PlayAdapter {

    private Context ctx;
    private JSONObject json;

    public PlayAdapter(Context ctx, JSONObject array) {
	this.ctx = ctx;
	this.json = array;

    }

}
