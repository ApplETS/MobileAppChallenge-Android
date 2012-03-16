package com.applets.mobile.challenge;

import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.applets.mobile.challenge.utils.IAsyncTaskListener;
import com.applets.mobile.challenge.utils.JSONRetreiver;

public class MediaControllerActivity extends Activity implements
	IAsyncTaskListener {

    private IAsyncTaskListener activity = this;

    private TextView songTextView;
    private TextView artistTextView;
    private TextView albumTextView;
    private TextView timeSongTextView;
    private ImageView albumImg;
    private Button previousBtn;
    private ImageButton rewBtn;
    private ImageButton playBtn;
    private ImageButton ffBtn;
    private ImageButton nextBtn;
    private SeekBar timeSeekBar;
    private Timer timer;

    private ImageButton pauseBtn;

    private String artist;

    private String album;

    private String song;

    @Override
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.media_controller);

	final Bundle b = getIntent().getExtras();
	artist = b.getString("artist");
	album = b.getString("album");
	song = b.getString("song");

	// Info de la chanson
	// TODO modifier les setText pour avoir les infos courants
	songTextView = (TextView) findViewById(R.id.song_lbl);
	songTextView.setText(song);

	artistTextView = (TextView) findViewById(R.id.artist_lbl);
	artistTextView.setText(artist);

	albumTextView = (TextView) findViewById(R.id.album_lbl);
	albumTextView.setText(album);

	timeSongTextView = (TextView) findViewById(R.id.timeSongtextView);
	timeSongTextView.setText("00:00");

	// Image de l'album
	albumImg = (ImageView) findViewById(R.id.albumImg);
	// albumImg.setImageBitmap(bm);

	// Button
	// previousBtn = (Button) findViewById(R.id.previousBtn);
	// previousBtn.setOnClickListener(new OnClickListener() {
	//
	// @Override
	// public void onClick(View v) {
	// // TODO Auto-generated method stub
	// // new
	// // JSONRetreiver(activity).execute("http://highwizard.com:8080/"
	// // + "previous/");
	//
	// }
	// });

	rewBtn = (ImageButton) findViewById(R.id.rewBtn);
	rewBtn.setOnClickListener(new OnClickListener() {

	    @Override
	    public void onClick(View v) {
		new JSONRetreiver(new IAsyncTaskListener() {

		    @Override
		    public void onPostExecute(JSONObject result) {
			try {
			    String time = result.getString("time");
			    timeSongTextView.setText(time);
			} catch (JSONException e) {
			    e.printStackTrace();
			}

		    }
		}).execute("http://highwizard.com:8080/",
			"player/seek/?time=-10");
	    }
	});

	playBtn = (ImageButton) findViewById(R.id.playBtn);
	playBtn.setOnClickListener(new OnClickListener() {

	    @Override
	    public void onClick(View v) {
		new JSONRetreiver(new IAsyncTaskListener() {

		    @Override
		    public void onPostExecute(JSONObject result) {
			try {
			    String s = result.getString("playing");
			    String t = result.getString("time");
			    timeSeekBar.setMax(100 * Integer.parseInt(t));
			    startTimer();
			    pauseBtn.setEnabled(true);
			    playBtn.setEnabled(false);
			} catch (JSONException e) {
			    e.printStackTrace();
			}
		    }
		}).execute("http://highwizard.com:8080/", "player/song/?file="
			+ artist + "/" + album + "/" + song);
	    }
	});

	pauseBtn = (ImageButton) findViewById(R.id.pauseBtn);
	pauseBtn.setOnClickListener(new OnClickListener() {

	    @Override
	    public void onClick(View v) {
		// TODO Auto-generated method stub
		new JSONRetreiver(new IAsyncTaskListener() {

		    @Override
		    public void onPostExecute(JSONObject result) {
			try {
			    String s = result.getString("isPlaying");
			} catch (JSONException e) {
			    e.printStackTrace();
			}
			pauseBtn.setEnabled(false);
			playBtn.setEnabled(false);
			timer.cancel();
		    }
		}).execute("http://highwizard.com:8080/", "player/pause/");
	    }
	});

	ffBtn = (ImageButton) findViewById(R.id.ffBtn);
	ffBtn.setOnClickListener(new OnClickListener() {

	    @Override
	    public void onClick(View v) {
		new JSONRetreiver(new IAsyncTaskListener() {

		    @Override
		    public void onPostExecute(JSONObject result) {
			try {
			    String time = result.getString("time");
			    timeSongTextView.setText(time);
			} catch (JSONException e) {
			    e.printStackTrace();
			}
		    }
		}).execute("http://highwizard.com:8080/",
			"player/seek/?time=10");
	    }
	});

	nextBtn = (ImageButton) findViewById(R.id.nextBtn);
	nextBtn.setOnClickListener(new OnClickListener() {

	    @Override
	    public void onClick(View v) {
		new JSONRetreiver(new IAsyncTaskListener() {

		    @Override
		    public void onPostExecute(JSONObject result) {
			try {
			    String playing = result.getString("isPlaying");
			    if (playing.equals("false")) {
				playBtn.setEnabled(true);
				pauseBtn.setEnabled(false);
				Toast.makeText(getApplication(),
					"Nothing to play.", Toast.LENGTH_LONG)
					.show();
			    }
			    timer.cancel();
			    startTimer();
			} catch (JSONException e) {
			    e.printStackTrace();
			}

		    }
		}).execute("http://highwizard.com:8080/", "player/next/");
	    }
	});

	// Time
	timeSeekBar = (SeekBar) findViewById(R.id.timeSeekBar);
	timeSeekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

	    @Override
	    public void onStopTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub

	    }

	    @Override
	    public void onStartTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub

	    }

	    @Override
	    public void onProgressChanged(SeekBar seekBar, int progress,
		    boolean fromUser) {

		new JSONRetreiver(new IAsyncTaskListener() {

		    @Override
		    public void onPostExecute(JSONObject result) {
		    }
		}).execute("http://highwizard.com:8080/", "seek/?time="
			+ progress);
	    }
	});

    }

    public void startTimer() {
	// Reference :
	// http://www.anddev.org/video-tut_-_playing_mediamp3_on_the_emulator-t156-s60.html
	timer.schedule(new TimerTask() {

	    @Override
	    public void run() {
		// TODO Auto-generated method stub
		int min = 0;
		int sec = 0;
		String str = "00:00";

		int curPos = 0;// local_mp.getCurrentPosition();

		timeSeekBar.setProgress(curPos);

		/* calculate the seconds elapsed */
		sec = (curPos % 60000) / 1000;

		/* calculate the minutes elapsed */
		min = curPos / 60000;

		/* print the position in mm:ss format */
		if (min < 10) {
		    if (sec < 10)
			str = "0" + min + ":0" + sec;
		    else
			str = "0" + min + ":" + sec;
		} else {
		    if (sec < 10)
			str = min + ":0" + sec;
		    else
			str = min + ":" + sec;
		}

		timeSongTextView.setText(str);

	    }
	}, 1000, 1000);
    }

    @Override
    public void onPostExecute(JSONObject result) {
	// TODO Auto-generated method stub

    }

    private void updatePlayButton(boolean playing) {
	playBtn.setImageResource(playing ? android.R.drawable.ic_media_pause
		: android.R.drawable.ic_media_play);
    }

}
