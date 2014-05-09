package com.hlresidential.aceyourexam;



import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.RelativeLayout;
import android.widget.Toast;



public class ReportActivity extends MyActivity {

	//onSaveInstanceState(
	// Gestures
	private GestureDetector gesturedetector = null;
	RelativeLayout layout;
	private boolean bTouch = false;
	// Preferences
	SharedPreferences preferences;
	private SharedPreferences.Editor editor = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setPreference(PREF_LAST_SCREEN, PREF_LAST_SCREEN_REPORT);
		setContentView(R.layout.activity_report);
		
		layout = (RelativeLayout) findViewById(R.id.relativecontainer);
		gesturedetector = new GestureDetector(new MyGestureListener());

		
		layout.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					bTouch = true;
					return false;
				} else {
					gesturedetector.onTouchEvent(event);
					bTouch = false;
					return true;
				}
			}

		}); 

	}
	
	public boolean dispatchTouchEvent(MotionEvent ev) {

		super.dispatchTouchEvent(ev);

		return gesturedetector.onTouchEvent(ev);

	}

	class MyGestureListener extends GestureDetector.SimpleOnGestureListener {

		private static final int SWIPE_MIN_DISTANCE = 150;
		private static final int SWIPE_MAX_OFF_PATH = 100;
		private static final int SWIPE_THRESHOLD_VELOCITY = 500;





		@Override
		    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
		        return false;
		    }
		
		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
			float dX = e2.getX() - e1.getX();
			float dY = e1.getY() - e2.getY();
			if (Math.abs(dY) < SWIPE_MAX_OFF_PATH &&

			Math.abs(velocityX) >= SWIPE_THRESHOLD_VELOCITY &&

			Math.abs(dX) >= SWIPE_MIN_DISTANCE) {

				if (dX > 0) {
//					Toast.makeText(getApplicationContext(), "<",
//							Toast.LENGTH_SHORT).show();
					finish();
				} else {
//					Toast.makeText(getApplicationContext(), ">",
//							Toast.LENGTH_SHORT).show();
//					finish();
				}
				return true;

			} else if (Math.abs(dX) < SWIPE_MAX_OFF_PATH &&
			Math.abs(velocityY) >= SWIPE_THRESHOLD_VELOCITY &&
			Math.abs(dY) >= SWIPE_MIN_DISTANCE) {

				if (dY > 0) {
					Toast.makeText(getApplicationContext(), "!^",
							Toast.LENGTH_SHORT).show();
					
				} else {
					Toast.makeText(getApplicationContext(), "!|",
							Toast.LENGTH_SHORT).show();
					
				}
				return false;

			}
			return false;

		}

	}
	private OnClickListener btnOnClickListener = new OnClickListener() {

	    @Override
	    public void onClick(View arg0) {
	        if (bTouch){
	            // Do Your OnClickJob Here without touch and move
	        	
	        }
	        

	    }
	}; 

	private void setPreference(String prefname, String prefvalue) {
		preferences = PreferenceManager
				.getDefaultSharedPreferences(ReportActivity.this);
		editor = preferences.edit();
		editor.putString(prefname, prefvalue);
		editor.commit();
	}

}
