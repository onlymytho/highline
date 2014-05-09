package com.hlresidential.aceyourexam;



import android.app.FragmentTransaction;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;



public class PostTestActivity extends MyActivity {

//	Button btn_start, btn_study, btn_test, btn_report, btn_aftyeryourexam;
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
	
	ContentResolver cr; 
	ContentValues cv;
	private static String str_where = HLdb.KEY_ROWID + " = ?";
	String lastTestID;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		preferences = PreferenceManager
				.getDefaultSharedPreferences(PostTestActivity.this);
		setPreference(PREF_LAST_SCREEN, PREF_LAST_SCREEN_TEST);
		lastTestID  = preferences.getString(PREF_LAST_TEST_ID, " ");
//		Toast.makeText(this, "Last Test ID: " + lastTestID, Toast.LENGTH_LONG);
		setContentView(R.layout.activity_posttest);
		layout = (RelativeLayout) findViewById(R.id.relativecontainer);
		gesturedetector = new GestureDetector(new MyGestureListener());

		
		TextView tv_title = (TextView) findViewById(R.id.tv_title);
		if(!lastTestID.equals(" ")){
			tv_title.setText("TEST " + lastTestID);
		}
			
		
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
//		int seconds = (int) (cursor.getInt(cursor.getColumnIndexOrThrow(HLdb.COL_NO_MILLISECONDS)) / 1000); 

//		if (cursor != null) {
//			tv_test_id.setText("TEST " + cursor.getString(cursor.getColumnIndexOrThrow(HLdb.KEY_ROWID)) + ". ");
//			tv_test_id_only.setText(cursor.getString(cursor.getColumnIndexOrThrow(HLdb.KEY_ROWID)));
//			tv_time.setText("" + (seconds / 3600) + ":" + ((seconds % 3600) / 60) + ":" + ((seconds % 3600) % 60));
		
//		frag1 = new StudyFragment();
//		fragTrans.addToBackStack(null);
//		fragTrans = getFragmentManager().beginTransaction();
//		fragTrans.add(R.id.frag_screen, frag1);
//		fragTrans.commit();
		
		cr = getContentResolver();
		String[] projection2 = new String[] { HLdb.COL_NO_MILLISECONDS, HLdb.COL_NO_CORRECT, HLdb.COL_NO_QUESTIONS};
		String[] selectionArg2 = new String[] { lastTestID };

		Cursor query = cr.query(HLProvider.CONTENT_URI_TESTS, projection2, str_where,
				selectionArg2, null) ;

		int noOfRows = 0;
		if (query.getCount() > 0) {
			noOfRows = query.getCount();
			query.moveToLast();
			TextView tv_time = (TextView) findViewById(R.id.tv_time);
			TextView tv_no_correct = (TextView) findViewById(R.id.tv_no_correct);
			TextView tv_recommendation = (TextView) findViewById(R.id.tv_recommendation);
			
			
			int seconds = (int) (query.getInt(query.getColumnIndexOrThrow(HLdb.COL_NO_MILLISECONDS)) / 1000);
			tv_time.setText("" + (seconds / 3600) + "h" + ((seconds % 3600) / 60) + "m" + ((seconds % 3600) % 60)+"s");
			tv_no_correct.setText(query.getString(query.getColumnIndexOrThrow(HLdb.COL_NO_CORRECT)));
			int no_correct = query.getInt(query.getColumnIndexOrThrow(HLdb.COL_NO_CORRECT));
			int TotalNoOfRows = query.getInt(query.getColumnIndexOrThrow(HLdb.COL_NO_QUESTIONS));
			
			float iPercentComplete = 0;
			try {
				iPercentComplete = ((float) no_correct / TotalNoOfRows) * 100;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
//			Toast.makeText(this, " % is : " +iPercentComplete + " = " + no_correct + " / " + TotalNoOfRows + " * 100", Toast.LENGTH_SHORT).show();
			String recommendation = "";
			if(iPercentComplete > -1 && iPercentComplete < 30) {
				recommendation = "Go back to STUDY !";
			} else if(iPercentComplete > 29 && iPercentComplete < 50) {
				recommendation = "Go back to PRACTICE !";
			} else if(iPercentComplete > 49 && iPercentComplete < 70) {
				recommendation = "Study more !";	
			} else if(iPercentComplete > 69 && iPercentComplete < 80) {
				recommendation = "Almost done !";
			}  else if(iPercentComplete > 79 && iPercentComplete < 96) {
				recommendation = "Congratulations ! Maintain this level !";
			} else {
				recommendation = "You are already an Agent !";
			}
			tv_recommendation.setText(recommendation);
			
			

			
			query.close();
		} else {
			query.close();
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
					setPreference(PREF_LAST_SCREEN, PREF_LAST_SCREEN_TEST);
					intent = new Intent(PostTestActivity.this, PreTestActivity.class);
//					intent.putExtra(PREF_LAST_SCREEN, prefLastScreen);
					startActivity(intent);
				} else {
//					Toast.makeText(getApplicationContext(), ">",
//							Toast.LENGTH_SHORT).show();

					Intent intent;
					setPreference(PREF_LAST_SCREEN, PREF_LAST_SCREEN_REPORT);
					intent = new Intent(PostTestActivity.this, ReportActivity.class);
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

	     //   }
	    }
	}; 
	
	private void setPreference(String prefname, String prefvalue) {
		preferences = PreferenceManager
				.getDefaultSharedPreferences(PostTestActivity.this);
		editor = preferences.edit();
		editor.putString(prefname, prefvalue);
		editor.commit();
	}

	@Override
	public void onBackPressed() {
			super.onBackPressed();

			Intent i = new Intent(PostTestActivity.this, PreTestActivity.class);
			i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(i);
			this.finish();

		}
	
}
