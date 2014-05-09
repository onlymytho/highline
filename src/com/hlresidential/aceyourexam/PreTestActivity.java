package com.hlresidential.aceyourexam;



import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;



public class PreTestActivity extends MyActivity {

	Button btn_start, btn_study, btn_test, btn_report, btn_aftyeryourexam;
	RelativeLayout rl;
	FragmentTransaction fragTrans = null;
	StudyFragment frag1;
	//onSaveInstanceState(
	// Gestures
	private GestureDetector gesturedetector = null;
	RelativeLayout layout;
	private boolean bTouch = false;
	
	//Shared Preferences
	SharedPreferences preferences;
	private SharedPreferences.Editor editor = null;
	private View myView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setPreference(PREF_LAST_SCREEN, PREF_LAST_SCREEN_TEST);
		setContentView(R.layout.activity_pretest);
		layout = (RelativeLayout) findViewById(R.id.relativecontainer);
		gesturedetector = new GestureDetector(new MyGestureListener());

		
		layout.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				myView = v;
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
		
		btn_start = (Button) findViewById(R.id.btn_start);
		btn_start.setOnClickListener(btnOnClickListener);
		
//		frag1 = new StudyFragment();
//		fragTrans.addToBackStack(null);
//		fragTrans = getFragmentManager().beginTransaction();
//		fragTrans.add(R.id.frag_screen, frag1);
//		fragTrans.commit();
	}
	
	public boolean dispatchTouchEvent(MotionEvent ev) {

		super.dispatchTouchEvent(ev);

		return gesturedetector.onTouchEvent(ev);

	}

	class MyGestureListener extends GestureDetector.SimpleOnGestureListener {

		private static final int SWIPE_MIN_DISTANCE = 150;
		private static final int SWIPE_MAX_OFF_PATH = 100;
		private static final int SWIPE_THRESHOLD_VELOCITY = 100;

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
					Intent intent;
					setPreference(PREF_LAST_SCREEN, PREF_LAST_SCREEN_PRACTICE);
					intent = new Intent(PreTestActivity.this, PracticeActivity.class);
//					intent.putExtra(PREF_LAST_SCREEN, prefLastScreen);
					startActivity(intent);
				} else {
//					Toast.makeText(getApplicationContext(), ">",
//							Toast.LENGTH_SHORT).show();

					Intent intent;
					setPreference(PREF_LAST_SCREEN, PREF_LAST_SCREEN_TEST);
					intent = new Intent(PreTestActivity.this, TestActivity.class);
//					intent.putExtra(PREF_LAST_SCREEN, prefLastScreen);
					startActivity(intent);
				}
				return true;

			} else if (Math.abs(dX) < SWIPE_MAX_OFF_PATH &&
			Math.abs(velocityY) >= SWIPE_THRESHOLD_VELOCITY &&
			Math.abs(dY) >= SWIPE_MIN_DISTANCE) {

				if (dY > 0) {
//					Toast.makeText(getApplicationContext(), "!^",
//							Toast.LENGTH_SHORT).show();
					
				} else {
//					Toast.makeText(getApplicationContext(), "!|",
//							Toast.LENGTH_SHORT).show();
					
				}
				return false;

			}
			return false;

		}

	}
	private OnClickListener btnOnClickListener = new OnClickListener() {

	    @Override
	    public void onClick(View v) {
//	        if (bTouch){
	            // Do Your OnClickJob Here without touch and move
	        	if(v == btn_start) {
	        		Intent intent;
					setPreference(PREF_LAST_SCREEN, PREF_LAST_SCREEN_TEST);
					intent = new Intent(PreTestActivity.this, TestActivity.class);
//					intent.putExtra(PREF_LAST_SCREEN, prefLastScreen);
					startActivity(intent);
	        	}
	     //   }
	    }
	}; 
	
	private void setPreference(String prefname, String prefvalue) {
		preferences = PreferenceManager
				.getDefaultSharedPreferences(PreTestActivity.this);
		editor = preferences.edit();
		editor.putString(prefname, prefvalue);
		editor.commit();
	}

}
