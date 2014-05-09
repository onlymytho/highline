package com.hlresidential.aceyourexam;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import java.util.Calendar;











import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Display;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class MainActivity extends MyActivity {

	// Preferences
	SharedPreferences preferences;
	private SharedPreferences.Editor editor = null;
	private boolean isInitialized = false;
	public static final String PREF_LAST_SCREEN = "prefLastScreen";
	private String prefLastScreen = " ";
	

	private Button btn_practice, btn_study, btn_test, btn_report, btn_afteryourexam, btn_submit, btn_prevm, btn_nextm;

	
	private ContentResolver cr;
	//onSaveInstanceState(
	// Gestures
	private GestureDetector gesturedetector = null;
	RelativeLayout layout;
	private boolean bTouch = false;

	TestDatePrefFragment frag;
    Calendar now;
	public final static String DIALOG_OK = "Yes";
	public final static String DIALOG_CANCEL = "No";
	final CharSequence[] itemsOK = { DIALOG_OK, DIALOG_CANCEL };
	int global_which_title = 0;
	
	  private AdView adView;

	  /* Your ad unit id. Replace with your actual ad unit id. */
	  private static final String AD_UNIT_ID = "ca-app-pub-4347043595760955/4815070822";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
        //Send tracker to Google Analytics.
//        Tracker t = ((BasicAndroidPlatform) this.getApplication()).getTracker(BasicAndroidPlatform.TrackerName.APP_TRACKER);
        //Set Screen Name
//        t.setScreenName("" + this.getClass().getName());
        //Send a screen view.
//        t.send(new HitBuilders.AppViewBuilder().build());

		
		setContentView(R.layout.activity_main);

		initializePreference();
		
		prefLastScreen = PREF_LAST_SCREEN_MAIN;
		btn_practice = (Button) findViewById(R.id.btn_practice);
		btn_practice.setOnClickListener(btnOnClickListener);
		btn_study = (Button) findViewById(R.id.btn_study);
		btn_study.setOnClickListener(btnOnClickListener);
		btn_test = (Button) findViewById(R.id.btn_test);
		btn_test.setOnClickListener(btnOnClickListener);
		btn_report = (Button) findViewById(R.id.btn_report);
		btn_report.setOnClickListener(btnOnClickListener);
		btn_afteryourexam = (Button) findViewById(R.id.btn_afteryourexam);
		btn_afteryourexam.setOnClickListener(btnOnClickListener);
	
		
		ContentResolver cr = this.getContentResolver();
		ContentValues values = new ContentValues();

		String[] projection = new String[] { HLdb.COL_ID };
		
		Cursor query = cr.query(HLProvider.CONTENT_URI, projection, null,
				null, null);

		int noOfRows = 0;
		if (query.getCount() > 0) {
            noOfRows = query.getCount();
            Toast.makeText(getApplicationContext(), "Database populated with " + noOfRows + " rows.", Toast.LENGTH_SHORT).show();
			query.close();
		} else {
			query.close();
			Toast.makeText(getApplicationContext(), "Database population required.", Toast.LENGTH_SHORT).show();
		}
		
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
		
//		onConfirmDialogOK(0);
	}
	
	public void mySVBtnClicked(View v){
	    //someFragment.myClickMethod(v);
		if (v == btn_prevm) {
			Log.e("main", "Main - Previous button");
			ToReviewFragment fragment = (ToReviewFragment) getFragmentManager().findFragmentByTag("ToReview");
			fragment.onForeignButtonSelected(2);
		}  else if (v == btn_nextm) {
			Log.e("main", "Main - Previous button");
			ToReviewFragment fragment = (ToReviewFragment) getFragmentManager().findFragmentByTag("ToReview");
			fragment.onForeignButtonSelected(3);
		} 
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
					intent = new Intent(MainActivity.this, AboutUsActivity.class);
//					intent.putExtra(PREF_LAST_SCREEN, prefLastScreen);
					startActivity(intent);
				} else {
//					Toast.makeText(getApplicationContext(), ">",
//							Toast.LENGTH_SHORT).show();
					Intent intent;
					setPreference(PREF_LAST_SCREEN, PREF_LAST_SCREEN_PRACTICE);
					intent = new Intent(MainActivity.this, ActivityToReview.class);
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

	
	Button.OnClickListener btnOnClickListener = new Button.OnClickListener() {
		

		@Override
		public void onClick(View v) {
			Bundle b = null;
			Intent intent;
			  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                  b = ActivityOptions.makeScaleUpAnimation(v, 0, 0, v.getWidth()/2, v.getHeight()/2).toBundle();
//                   b = ActivityOptions.makeScaleUpAnimation(v, (int)(v.getX() + v.getWidth()  / 2), (int)(v.getY() + v.getHeight() / 2), v.getWidth()/2, v.getHeight()/2).toBundle();
              }
			if (v == btn_practice) {
              
				setPreference(PREF_LAST_SCREEN, PREF_LAST_SCREEN_PRACTICE);
				intent = new Intent(MainActivity.this, PracticeActivity.class);
				intent.putExtra(PREF_LAST_SCREEN, prefLastScreen);
				startActivity(intent, b);
			} else if (v == btn_study) {
				prefLastScreen = PREF_LAST_SCREEN_STUDY;
				setPreference(PREF_LAST_SCREEN, PREF_LAST_SCREEN_STUDY);
				intent = new Intent(MainActivity.this, StudyActivity.class);
				startActivity(intent, b);
			} else if (v == btn_test) {
				setPreference(PREF_LAST_SCREEN, PREF_LAST_SCREEN_TEST);
				intent = new Intent(MainActivity.this, PreTestActivity.class);
				startActivity(intent, b);
			} else if (v == btn_report) {
				setPreference(PREF_LAST_SCREEN, PREF_LAST_SCREEN_REPORT);
				intent = new Intent(MainActivity.this, ReportActivity.class);
				startActivity(intent, b);
			} else if (v == btn_afteryourexam) {
				intent = new Intent(MainActivity.this, AfterYourExamActivity.class);
				startActivity(intent, b);
			}  
			
//			else if (v == btn_submit) {
//				ToReviewFragment fragment = (ToReviewFragment) getFragmentManager().findFragmentByTag("ToReview");
//				fragment.onForeignButtonSelected(1);
//			}   
			
		}
	};



	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	    if (adView != null) {
	        adView.resume();
	      }
//		loadPreferences();
//		prefLastScreen = PREF_LAST_SCREEN_MAIN;
//		post(String.valueOf(prefLastScreen));
//		setPreference(PREF_LAST_SCREEN, PREF_LAST_SCREEN_MAIN);
		
	}
	
	
	
	@Override
	public void onPause() {
	    if (adView != null) {
	        adView.pause();
	      }
		super.onPause();
		savePreferences();
	}



	public void initializePreference() {
		if (!isInitialized) {
			preferences = PreferenceManager
					.getDefaultSharedPreferences(MainActivity.this);
			editor = preferences.edit();
			editor.commit();
			loadPreferences();
			isInitialized = true;
		}
	}
	private void savePreferences() {
		editor.putString(PREF_LAST_SCREEN, prefLastScreen);
		editor.commit();
	}

	private void loadPreferences() {
		prefLastScreen = preferences.getString(PREF_LAST_SCREEN, " ");


	}
	private void setPreference(String prefname, String prefvalue) {
		editor.putString(prefname, prefvalue);
		editor.commit();
	}
	
    public interface TestDatePrefFragmentListener{
    	//this interface is a listener between the Date Dialog fragment and the activity to update the buttons date
    	public void updateChangedDate(int year, int month, int day);
    	
    }
	
   public void showDialog() {
	FragmentTransaction ft = getFragmentManager().beginTransaction(); //get the fragment
	frag = TestDatePrefFragment.newInstance(this, new TestDatePrefFragmentListener(){
		public void updateChangedDate(int year, int month, int day){
			
			now.set(year, month, day);
		}
	}, now);
	
	frag.show(ft, "TestDateDialogFragment");
	
}	
   
	private void onConfirmDialogOK(int which_title) {
		LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View customTitleView = inflater.inflate(
				R.layout.alertdialog_testdate_title_confirm, null);
		global_which_title = which_title;
		switch (which_title) {
		case 1:
			// already set from above

			break;

		}

		AlertDialog.Builder confirmDialog = new AlertDialog.Builder(
				MainActivity.this);
		confirmDialog.setCustomTitle(customTitleView).setItems(itemsOK,
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						if (itemsOK[which].equals(DIALOG_OK)) {
							now = Calendar.getInstance();
							showDialog();
						} else {
							
						}

					}
				});

		confirmDialog.show();
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



