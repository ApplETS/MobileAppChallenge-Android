package com.applets.mobile.challenge.utils;

import org.json.JSONObject;

import android.content.Context;
import android.widget.ListAdapter;

import com.applets.mobile.challenge.adapters.ArtistAdapter;
import com.applets.mobile.challenge.adapters.ListAlbumAdapter;
import com.applets.mobile.challenge.adapters.PlaylistAdapter;

public final class AdapterFactory {

    public static AdapterFactory instance;

    private AdapterFactory() {

    }

    public static AdapterFactory getInstance() {
	if (instance == null) {
	    instance = new AdapterFactory();
	}
	return instance;
    }

    public ListAdapter getAdapter(String type, Context ctx, JSONObject array) {
	if (type.equals("listalbums")) {
	    return new ListAlbumAdapter(ctx, array);
	} else if (type.equals("playlist")) {
	    return new PlaylistAdapter(ctx, array);
	}
	return new ArtistAdapter(ctx, array);
    }

}
