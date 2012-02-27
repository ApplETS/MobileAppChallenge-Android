package com.applets.mobile.challenge;

import com.applets.mobile.challenge.adapters.GridIconAdapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

public class MobileAppChallengeActivity extends Activity implements OnItemClickListener {
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        GridView g = (GridView)findViewById(R.id.gridView1);
        g.setAdapter(new GridIconAdapter(this));
        g.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
//	Intent intent = null;
	switch(position){
		default:
//		    intent = new Intent();
		    break;
	}
//	startActivity(intent);
    }
}