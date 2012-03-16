package com.applets.mobile.challenge;

import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.applets.mobile.challenge.adapters.PlayAdapter;
import com.applets.mobile.challenge.utils.IAsyncTaskListener;
import com.applets.mobile.challenge.utils.JSONRetreiver;

public class MediaControllerActivity extends Activity implements
		IAsyncTaskListener {

	private IAsyncTaskListener activity = this;

	private String file;

	private TextView songTextView;
	private TextView artistTextView;
	private TextView albumTextView;
	private TextView timeSongTextView;
	private ImageView albumImg;
	private ImageButton previousBtn;
	private ImageButton rewBtn;
	private ImageButton playBtn;
	private ImageButton ffBtn;
	private ImageButton nextBtn;
	private SeekBar timeSeekBar;
	private Timer timer = new Timer();
	private final Handler handler = new Handler();
	private String command;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.media_controller);

		file = this.getIntent().getExtras().getString("file");

		String[] info = file.split("/");
		
		// Info de la chanson
		// TODO modifier les setText pour avoir les infos courants
		songTextView = (TextView) findViewById(R.id.song_lbl);
		songTextView.setText(info[2]);

		artistTextView = (TextView) findViewById(R.id.artist_lbl);
		artistTextView.setText(info[0]);

		albumTextView = (TextView) findViewById(R.id.album_lbl);
		albumTextView.setText(info[1]);

		timeSongTextView = (TextView) findViewById(R.id.timeSongtextView);
		timeSongTextView.setText("00:00");

		// Image de l'album
		albumImg = (ImageView) findViewById(R.id.albumImg);
		// albumImg.setImageBitmap(bm);


		// Button
		previousBtn = (ImageButton) findViewById(R.id.previousBtn);
		previousBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// new
				// JSONRetreiver(activity).execute("http://highwizard.com:8080/"
				// + "previous/");

			}
		});

		rewBtn = (ImageButton) findViewById(R.id.rewBtn);
		rewBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// new
				// JSONRetreiver(activity).execute("http://highwizard.com:8080/"
				// + "player/");
			}
		});

		playBtn = (ImageButton) findViewById(R.id.playBtn);
		playBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				status();

				// get player state
				// new JSONRetreiver(activity)
				// .execute("http://highwizard.com:8080/player/");

				// new
				// JSONRetreiver(activity).execute("http://highwizard.com:8080/"
				// + "stop/");

				// joue le fichier dŽfinie par le path si il existe
				// new
				// JSONRetreiver(activity).execute("http://highwizard.com:8080/"
				// + "/play/song/?file=" + file);

				// si play --> partir le timer
				// si stop --> arreter le timer

			}
		});

		ffBtn = (ImageButton) findViewById(R.id.ffBtn);
		ffBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// new
				// JSONRetreiver(activity).execute("http://highwizard.com:8080/"
				// + "fastFoward/");
			}
		});

		nextBtn = (ImageButton) findViewById(R.id.nextBtn);
		nextBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				new JSONRetreiver(activity)
						.execute("http://highwizard.com:8080/player/" + "next");
			}
		});

		// Time
		timeSeekBar = (SeekBar) findViewById(R.id.timeSeekBar);
		// new JSONRetreiver(activity).execute("http://highwizard.com:8080/" +
		// "play/");
		timeSeekBar.setMax(100/* temps de la chanson */);
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
				// TODO envoyer nouvelle position

			}
		});

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

				final String temp = str;

				handler.post(new Runnable() {
					@Override
					public void run() {
						timeSongTextView.setText(temp);
					}
				});

			}
		}, 1000, 1000);
	}

	private void status(){
		command = "play";
		new JSONRetreiver(activity).execute("http://highwizard.com:8080"
				+ "/player/");
		
	}
	
	private void play(boolean value) {
		if (value) {
			new JSONRetreiver(activity).execute("http://highwizard.com:8080"
					+ "/player/pause");
			updatePlayButton(false);
		} else {
			new JSONRetreiver(activity).execute("http://highwizard.com:8080"
					+ "/player/song", "file=" + file);
			updatePlayButton(true);
		}
	}

	@Override
	public void onPostExecute(final JSONObject result) {
		final Context ctx = this;
		handler.post(new Runnable() {
			@Override
			public void run() {
				PlayAdapter adapter = new PlayAdapter(ctx, result);
				if (command.equals("play")) {
					play(adapter.isPlaying());
				}
			}
		});
	}

	private void updatePlayButton(boolean playing) {
		playBtn.setImageResource(playing ? android.R.drawable.ic_media_pause
				: android.R.drawable.ic_media_play);
	}

}
