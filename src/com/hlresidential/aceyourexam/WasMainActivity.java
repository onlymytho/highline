package com.hlresidential.aceyourexam;

import java.util.Calendar;

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
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class WasMainActivity extends MyActivity {

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
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
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
		btn_submit = (Button) findViewById(R.id.btn_submit);
		btn_submit.setOnClickListener(btnOnClickListener);
		btn_nextm = (Button) findViewById(R.id.btn_SV_next);
		btn_prevm = (Button) findViewById(R.id.btn_SV_prev);
		
		
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

		onConfirmDialogOK(0);
	}
	
 /**   public void showDialog() {
    	FragmentTransaction ft = getFragmentManager().beginTransaction(); //get the fragment
    	frag = TestDatePrefFragment.newInstance(this, new TestDatePrefFragmentListener(){
    		public void updateChangedDate(int year, int month, int day){
    			
    			now.set(year, month, day);
    		}
    	}, now);
    	
    	frag.show(ft, "TestDateDialogFragment");
    	
    }**/	
	
	
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
					intent = new Intent(WasMainActivity.this, AboutUsActivity.class);
//					intent.putExtra(PREF_LAST_SCREEN, prefLastScreen);
					startActivity(intent);
				} else {
//					Toast.makeText(getApplicationContext(), ">",
//							Toast.LENGTH_SHORT).show();
					Intent intent;
					setPreference(PREF_LAST_SCREEN, PREF_LAST_SCREEN_PRACTICE);
					intent = new Intent(WasMainActivity.this, ActivityToReview.class);
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
				intent = new Intent(WasMainActivity.this, PracticeActivity.class);
				intent.putExtra(PREF_LAST_SCREEN, prefLastScreen);
				startActivity(intent, b);
			} else if (v == btn_study) {
				prefLastScreen = PREF_LAST_SCREEN_STUDY;
				setPreference(PREF_LAST_SCREEN, PREF_LAST_SCREEN_STUDY);
				intent = new Intent(WasMainActivity.this, StudyActivity.class);
				startActivity(intent, b);
			} else if (v == btn_test) {
				setPreference(PREF_LAST_SCREEN, PREF_LAST_SCREEN_TEST);
				intent = new Intent(WasMainActivity.this, PreTestActivity.class);
				startActivity(intent, b);
			} else if (v == btn_report) {
				setPreference(PREF_LAST_SCREEN, PREF_LAST_SCREEN_REPORT);
				intent = new Intent(WasMainActivity.this, ReportActivity.class);
				startActivity(intent, b);
			} else if (v == btn_afteryourexam) {
				intent = new Intent(WasMainActivity.this, AfterYourExamActivity.class);
				startActivity(intent, b);
			}   else if (v == btn_submit) {
				ToReviewFragment fragment = (ToReviewFragment) getFragmentManager().findFragmentByTag("ToReview");
				fragment.onForeignButtonSelected(1);
			}   
			
		}
	};



	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
//		loadPreferences();
//		prefLastScreen = PREF_LAST_SCREEN_MAIN;
//		post(String.valueOf(prefLastScreen));
//		setPreference(PREF_LAST_SCREEN, PREF_LAST_SCREEN_MAIN);
		
	}
	
	
	
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		savePreferences();
	}



	public void initializePreference() {
		if (!isInitialized) {
			preferences = PreferenceManager
					.getDefaultSharedPreferences(WasMainActivity.this);
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
				WasMainActivity.this);
		confirmDialog.setCustomTitle(customTitleView).setItems(itemsOK,
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						if (itemsOK[which].equals(DIALOG_OK)) {
							now = Calendar.getInstance();
	//						showDialog();
						} else {
							
						}

					}
				});

		confirmDialog.show();
	}
}



