package com.applets.mobile.challenge.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.AsyncTask;
import android.util.Log;

public class JSONRetreiver extends AsyncTask<String, Void, Void> {
	private IAsyncTaskListener listener;

	public JSONRetreiver(final IAsyncTaskListener listener) {
		this.listener = listener;
	}

	@Override
	protected Void doInBackground(String... params) {
		StringBuilder builder = new StringBuilder();
		HttpClient client = new DefaultHttpClient();
		HttpGet get = new HttpGet(params[0]);

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
					builder.append(line);
				}
			} else {
				Log.e(JSONRetreiver.class.toString(), "Failed to download file");
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	protected void onPostExecute(Void result) {
		listener.onPostExecute();
		super.onPostExecute(result);
	}
}
