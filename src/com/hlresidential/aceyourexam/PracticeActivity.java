package com.hlresidential.aceyourexam;



import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
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

public class PracticeActivity extends MyActivity {

	public interface UserDirection {
		public void onUserDirection(String direction);
	}

	private static final String TAG = PracticeActivity.class.getSimpleName();
	Button btn_practice, btn_study, btn_test, btn_report, btn_aftyeryourexam,
			btn_submit, btn_prevm, btn_nextm;
	RelativeLayout rl;
	FragmentTransaction fragTrans = null;
	Fragment frag = null;
	PracticeFragment frag_practice;
	public static final String PREF_LAST_SCREEN = "prefLastScreen";
	public static final String PREF_LAST_SCREEN_PRACTICE = "prefLastScreenPractice";

	private SharedPreferences.Editor editor = null;
	String prefLastScreen = " ";

	// onSaveInstanceState(
	// Gestures
	private GestureDetector gesturedetector = null;
	RelativeLayout layout;
	private boolean bTouch = false;
	
	  private AdView adView;

	  /* Your ad unit id. Replace with your actual ad unit id. */
	  private static final String AD_UNIT_ID = "ca-app-pub-4347043595760955/4815070822";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Bundle extras = getIntent().getExtras();
		// if (extras != null) {
		// Log.i("PracticeActivity", " 022:... we got some extras");
		// prefLastScreen = getIntent().getStringExtra(PREF_LAST_SCREEN);
		// }

		setPreference(PREF_LAST_SCREEN, PREF_LAST_SCREEN_PRACTICE);
		setContentView(R.layout.activity_practice);

		btn_submit = (Button) findViewById(R.id.btn_submit);
		// btn_submit.setOnClickListener(btnOnClickListener);
		btn_nextm = (Button) findViewById(R.id.btn_SV_next);
		btn_prevm = (Button) findViewById(R.id.btn_SV_prev);

		// rl = (RelativeLayout) findViewById(R.id.frag_practice_layout);
		// if (findViewById(R.id.frag_practice_layout) != null) {

		// However, if we're being restored from a previous state,
		// then we don't need to do anything and should return or else
		// we could end up with overlapping fragments.
		// if (savedInstanceState != null) {
		// return;
		// }
		// }
		// try {
		// rl.removeAllViews();
		// } catch (Exception e) {
		// TODO Auto-generated catch block
		// e.printStackTrace();
		// }

		layout = (RelativeLayout) findViewById(R.id.frag_practice_layout);
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

		if (findViewById(R.id.frag_practice_layout) != null) {

			// However, if we're being restored from a previous state,
			// then we don't need to do anything and should return or else
			// we could end up with overlapping fragments.
			if (savedInstanceState != null) {
				return;
			}
		}

		Bundle bundle = new Bundle();
		bundle.putString(PREF_LAST_SCREEN, PREF_LAST_SCREEN_PRACTICE);

		// frag_practice = new PracticeFragment();
		// try {
		// fragTrans.addToBackStack(PREF_LAST_SCREEN_PRACTICE);
		// } catch (Exception e) {
		// TODO Auto-generated catch block
		// e.printStackTrace();
		// }

		// fragTrans = getFragmentManager().beginTransaction();
		// fragTrans.add(rl.getId(), frag_practice);
		// fragTrans.addToBackStack(PREF_LAST_SCREEN_PRACTICE);
		// fragTrans.commit();

	}

	public void mySVBtnClicked(View v) {
		// someFragment.myClickMethod(v);
		if (v == btn_prevm) {
			Log.e("main", "Main - Previous button");
			PracticeFragment fragment = (PracticeFragment) getFragmentManager()
					.findFragmentByTag("Practice");
			fragment.onForeignButtonSelected(2);
		} else if (v == btn_nextm) {
			Log.e("main", "Main - Previous button");
			PracticeFragment fragment = (PracticeFragment) getFragmentManager()
					.findFragmentByTag("Practice");
			fragment.onForeignButtonSelected(3);
		} else if (v == btn_submit) {
			PracticeFragment fragment = (PracticeFragment) getFragmentManager()
					.findFragmentByTag("Practice");
			fragment.onForeignButtonSelected(1);
		}
	}

	private Activity getActivity() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	    if (adView != null) {
	        adView.resume();
	      }
		// ActivitySwipeDetector activitySwipeDetector = new
		// ActivitySwipeDetector(this);
		// rl = (RelativeLayout)this.findViewById(R.id.frag_practice_layout);
		// rl.setOnTouchListener(activitySwipeDetector);

	}
	
	@Override
	public void onPause() {
	    if (adView != null) {
	        adView.pause();
	      }
		super.onPause();
	}

	/*
	 * @Subscribe public void changeLastScreen(OttoActivityEventBus
	 * lastScreenChangedBusEvent) { String txt = " "; txt =
	 * lastScreenChangedBusEvent.getLastScreen();
	 * 
	 * // Log.v("PracticeActivity", "changeLastScreen(): " + txt + " ... " +
	 * lastScreenChangedBusEvent.getLastScreen()); //
	 * Toast.makeText(getApplicationContext(), "changeLastScreen(): " + txt,
	 * Toast.LENGTH_LONG).show(); }
	 */
	public boolean dispatchTouchEvent(MotionEvent ev) {

		super.dispatchTouchEvent(ev);

		return gesturedetector.onTouchEvent(ev);

	}

	class MyGestureListener extends GestureDetector.SimpleOnGestureListener {

		private static final int SWIPE_MIN_DISTANCE = 150;
		private static final int SWIPE_MAX_OFF_PATH = 100;
		private static final int SWIPE_THRESHOLD_VELOCITY = 100;

		@Override
		public boolean onScroll(MotionEvent e1, MotionEvent e2,
				float distanceX, float distanceY) {
			return false;
		}

		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
				float velocityY) {
			float dX = e2.getX() - e1.getX();
			float dY = e1.getY() - e2.getY();
			if (Math.abs(dY) < SWIPE_MAX_OFF_PATH &&

			Math.abs(velocityX) >= SWIPE_THRESHOLD_VELOCITY &&

			Math.abs(dX) >= SWIPE_MIN_DISTANCE) {

				if (dX > 0) {
					// Toast.makeText(getApplicationContext(), "<",
					// Toast.LENGTH_SHORT).show();
					Intent intent;
					setPreference(PREF_LAST_SCREEN, PREF_LAST_SCREEN_PRACTICE);
					intent = new Intent(PracticeActivity.this,
							StudyActivity.class);

				} else {
					// Toast.LENGTH_SHORT).show();
					finish();
//					Intent intent;
//					setPreference(PREF_LAST_SCREEN, PREF_LAST_SCREEN_PRACTICE);
//					intent = new Intent(PracticeActivity.this,
//							StudyActivity.class);
					// intent.putExtra(PREF_LAST_SCREEN, prefLastScreen);
//					startActivity(intent);
					// Toast.makeText(getApplicationContext(), ">",
					// Toast.LENGTH_SHORT).show();
					// PracticeFragment fragment = (PracticeFragment)
					// getFragmentManager()
					// .findFragmentById(R.id.frag_practice);
					// fragment.onUserDirection("Next");
				}
				return true;

			} else if (Math.abs(dX) < SWIPE_MAX_OFF_PATH
					&& Math.abs(velocityY) >= SWIPE_THRESHOLD_VELOCITY
					&& Math.abs(dY) >= SWIPE_MIN_DISTANCE) {

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
		public void onClick(View v) {
			if (bTouch) {
				// Do Your OnClickJob Here without touch and move

			}

		}
	};

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);

		// Since the pager fragments don't have known tags or IDs, the only way
		// to persist the
		// reference is to use putFragment/getFragment. Remember, we're not
		// persisting the exact
		// Fragment instance. This mechanism simply gives us a way to persist
		// access to the
		// 'current' fragment instance for the given fragment (which changes
		// across orientation
		// changes).
		//
		// The outcome of all this is that the "Refresh" menu button refreshes
		// the stream across
		// orientation changes.
		if (frag_practice != null) {
			getFragmentManager().putFragment(outState,
					PREF_LAST_SCREEN_PRACTICE, frag_practice);
		}
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		if (frag_practice == null) {
			frag = getFragmentManager().getFragment(savedInstanceState,
					PREF_LAST_SCREEN_PRACTICE);

		}
	}

	private void setPreference(String prefname, String prefvalue) {
		preferences = PreferenceManager
				.getDefaultSharedPreferences(PracticeActivity.this);
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
