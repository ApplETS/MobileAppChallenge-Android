package com.applets.mobile.challenge.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;

public class JSONRetreiver extends AsyncTask<String, Integer, JSONObject> {
	private IAsyncTaskListener listener;

	public JSONRetreiver(final IAsyncTaskListener listener) {
		this.listener = listener;
	}
<<<<<<< HEAD
	Log.i("Challenge", get.getURI().toString());
	JSONObject json = new JSONObject();
	try {
	    HttpResponse r = client.execute(get);
	    StatusLine status = r.getStatusLine();
=======
>>>>>>> dab674688c3b3efcae2286b1b8a37d84c7f8ea65

	@Override
	/**
	 * params[1] passer les valeur get comme ceci : key=value; key2=value2
	 * TODO code for post
	 */
	protected JSONObject doInBackground(String... params) {
		onPreExecute();
		StringBuilder builder = new StringBuilder();
		HttpClient client = new DefaultHttpClient();
		HttpGet get = new HttpGet(params[0]);
		if (params.length > 1) {
			List<NameValuePair> getParams = new ArrayList<NameValuePair>();
			String[] strParams = params[1].split(";");
			for (int i = 0; i < strParams.length; i++) {
				String[] tempKeyValue = strParams[i].split("=");
				getParams.add(new BasicNameValuePair(tempKeyValue[0],
						tempKeyValue[1]));
			}
			String paramString = URLEncodedUtils.format(getParams, "utf-8");
			get = new HttpGet(params[0] + "?" + paramString);
		}
		Log.i("Challenge", get.getURI().toString());
		JSONObject json = null;
		try {
			HttpResponse r = client.execute(get);
			StatusLine status = r.getStatusLine();

			if (status.getStatusCode() == 200) {
				HttpEntity entity = r.getEntity();
				InputStream content = entity.getContent();
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(content));
				String line;
				while ((line = reader.readLine()) != null) {
					onProgressUpdate(1);
					builder.append(line);
				}
				reader.close();
				try {
					json = new JSONObject(builder.toString());
				} catch (JSONException e) {
					onCancelled();
					Log.e(JSONRetreiver.class.toString(),
							"Failed to parse data :" + params[0]);
				}
			} else {
				onCancelled();
				Log.e(JSONRetreiver.class.toString(), "Failed to download file");
			}

		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();

		}

		return json;
	}

	@Override
	protected void onPostExecute(JSONObject result) {
		listener.onPostExecute(result);
	}
}
