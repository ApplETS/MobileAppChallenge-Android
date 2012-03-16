package com.applets.mobile.challenge.utils;

import org.json.JSONObject;

import android.content.Context;
import android.widget.ListAdapter;

import com.applets.mobile.challenge.adapters.AlbumAdapter;
import com.applets.mobile.challenge.adapters.ArtistAdapter;
<<<<<<< HEAD
import com.applets.mobile.challenge.adapters.PlaylistAdapter;
=======
import com.applets.mobile.challenge.adapters.PlayAdapter;
>>>>>>> dab674688c3b3efcae2286b1b8a37d84c7f8ea65

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

<<<<<<< HEAD
    public ListAdapter getAdapter(String type, Context ctx, JSONObject array) {
	if(type.equals("albums")){
	    return new AlbumAdapter(ctx, array);
	}else if(type.equals("playlist")){
	    return new PlaylistAdapter(ctx, array);   
	}
	return new ArtistAdapter(ctx, array);
    }
=======
	public ListAdapter getAdapter(String type, Context ctx, JSONObject array) {
		return new ArtistAdapter(ctx, array);
	}
>>>>>>> dab674688c3b3efcae2286b1b8a37d84c7f8ea65
}
