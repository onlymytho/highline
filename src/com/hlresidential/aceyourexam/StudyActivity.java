package com.hlresidential.aceyourexam;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;



public class StudyActivity extends MyActivity {

	Button btn_practice, btn_study, btn_test, btn_report, btn_aftyeryourexam;
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
	  private AdView adView;

	  /* Your ad unit id. Replace with your actual ad unit id. */
	  private static final String AD_UNIT_ID = "ca-app-pub-4347043595760955/4815070822";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setPreference(PREF_LAST_SCREEN, PREF_LAST_SCREEN_STUDY);
		setContentView(R.layout.activity_study);
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
		
		  // Create an ad.
	    adView = new AdView(this);
	    adView.setAdSize(AdSize.BANNER);
	    adView.setAdUnitId(AD_UNIT_ID);
	    
	    RelativeLayout.LayoutParams params= new RelativeLayout.LayoutParams(
	            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
	    params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
          
	    adView.setLayoutParams(params);
	    // Add the AdView to the view hierarchy. The view will have no size
	    // until the ad is loaded.
	    LinearLayout llayout = (LinearLayout) findViewById(R.id.linearLayout); // id.linearLayout
	    layout.addView(adView);

	    // Create an ad request. Check logcat output for the hashed device ID to
	    // get test ads on a physical device.
	    AdRequest adRequest = new AdRequest.Builder()
	        .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
//	        .addTestDevice("INSERT_YOUR_HASHED_DEVICE_ID_HERE")
	        .build();

	    // Start loading the ad in the background.
	    adView.loadAd(adRequest);
		
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
					finish();
					
				} else {
//					Toast.makeText(getApplicationContext(), ">",
//							Toast.LENGTH_SHORT).show();
					Intent intent;
					setPreference(PREF_LAST_SCREEN, PREF_LAST_SCREEN_PRACTICE);
					intent = new Intent(StudyActivity.this, PracticeActivity.class);
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
	    public void onClick(View arg0) {
	        if (bTouch){
	            // Do Your OnClickJob Here without touch and move
	        	
	        }
	    }
	}; 
	
	@Override
	public void onResume() {
		super.onResume();
	    if (adView != null) {
	        adView.resume();
	      }

	}
	
	
	
	@Override
	public void onPause() {
	    if (adView != null) {
	        adView.pause();
	      }
		super.onPause();

	}
	
	private void setPreference(String prefname, String prefvalue) {
		preferences = PreferenceManager
				.getDefaultSharedPreferences(StudyActivity.this);
		editor = preferences.edit();
		editor.putString(prefname, prefvalue);
		editor.commit();
	}
	  @Override
	  public void onDestroy() {
	    // Destroy the AdView.
	    if (adView != null) {
	      adView.destroy();
	    }
	    super.onDestroy();
	  }
}
