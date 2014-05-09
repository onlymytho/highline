package com.hlresidential.aceyourexam;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.hlresidential.aceyourexam.TestFragment.OnSubmitListener;
import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


public class TestActivity extends MyActivity implements OnSubmitListener {
	
	private Map<Integer, Integer> map =  new HashMap<Integer, Integer>();
	private Integer currentLastAnswer;
	
	private Map<Integer, Boolean> mapInitialized =  new HashMap<Integer, Boolean>();
	private Integer currentInitialized;

	Button btn_pauser;
	ImageView iv_pause;
	RelativeLayout rl;
	FragmentTransaction fragTrans = null;
	Fragment frag1;
	Animator anim;
	ContentValues cv;
	private Cursor mGroupCursor = null;
	// onSaveInstanceState(
	// Gestures
	private GestureDetector gesturedetector = null;
	RelativeLayout layout;
	private boolean bTouch = false;
	private boolean bTouchEvent = true;

	public final static String DIALOG_CONTINUE = "Contine";
	public final static String DIALOG_ABORT = "Abort Test (No Results!)";
	public final static String DIALOG_PAUSE = "Pause";
	public final static String DIALOG_SUBMIT = "Submit";
	final CharSequence[] items = { DIALOG_CONTINUE, DIALOG_PAUSE, DIALOG_ABORT,
			DIALOG_SUBMIT };
	boolean isPaused = false;
	boolean isStopTimer = false;
	final CharSequence[] itemsAgainAbort = { DIALOG_CONTINUE, DIALOG_PAUSE,
			DIALOG_SUBMIT };
	final CharSequence[] itemsAgainPause = { DIALOG_CONTINUE, DIALOG_ABORT
			 };  //DIALOG_SUBMIT ??
	public final static String DIALOG_OK = "Yes";
	public final static String DIALOG_CANCEL = "No";
	final CharSequence[] itemsOK = { DIALOG_OK, DIALOG_CANCEL };
	public final static int DIALOG_TITLE_CONFIRM = 1;
	public final static int DIALOG_TITLE_PAUSE = 2;
	int global_which_title = 0;
	final CharSequence[] itemsSubmit = { "Saving your Test ... " };
	int iContinueCtr = 0;

	// Preferences
	SharedPreferences preferences;
	private SharedPreferences.Editor editor = null;

	CountDownTimer myTimer;
	long defaultTimerInMilli = 130000;
	long remainingTimeInMilli = 0;

	TextView tv_clock = null;
	View handler;
	int progressStatus = 0;
	int iBackCtr = 0;
	Button btn_practice, btn_experiment, btn_test, btn_report, btn_aftyeryourexam, btn_float_rb;


	

	private View myView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setPreference(PREF_LAST_SCREEN, PREF_LAST_SCREEN_TEST);
		setContentView(R.layout.activity_test);

		cv = new ContentValues();

		if (findViewById(R.id.relativecontainer) != null) {

			// However, if we're being restored from a previous state,
			// then we don't need to do anything and should return or else
			// we could end up with overlapping fragments.
			if (savedInstanceState != null) {
				return;
			}
		}
		
//		btn_float_rb = (Button) findViewById(R.id.btn_float_rb);
//		btn_float_rb.setOnClickListener(btnOnClickListener);

		btn_pauser = (Button) findViewById(R.id.btn_pauser);
		btn_pauser.setOnClickListener(btnOnClickListener);
		iv_pause = (ImageView) findViewById(R.id.iv_pause_img);
		// btn_pause.setBackgroundResource(R.drawable.selector_pause_btn);

		tv_clock = (TextView) findViewById(R.id.tv_clock);
		tv_clock.setTextColor(getResources().getColor(R.color.white));
		layout = (RelativeLayout) findViewById(R.id.relativecontainer);
		gesturedetector = new GestureDetector(new MyGestureListener());

		layout.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				bTouchEvent = true;
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					bTouch = true;
					bTouchEvent = false;
					return false;
				} else {
					gesturedetector.onTouchEvent(event);
					bTouch = false;
					bTouchEvent = true;
					return true;
				}
			}

		});

		remainingTimeInMilli = defaultTimerInMilli;
		onResumeTimer(remainingTimeInMilli);

		// frag1 = new StudyFragment();
		// fragTrans.addToBackStack(null);
		// fragTrans = getFragmentManager().beginTransaction();
		// fragTrans.add(R.id.frag_screen, frag1);
		// fragTrans.commit();

	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		bTouch = false;
		bTouchEvent = true;
		if (isPaused) {
			onResumeTimer(remainingTimeInMilli);
		}
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		TextView tv_clock = (TextView) findViewById(R.id.tv_clock);
		tv_clock.setTextColor(getResources().getColor(R.color.white));
		if (!isPaused) {
			onPauseTimer();
		}

	}

	public void onCountDown() {
		ImageView iv_hourglass = (ImageView) findViewById(R.id.iv_hourglass);
		// TODO need left_at_end animation
		anim = AnimatorInflater.loadAnimator(this, R.anim.spin);
		anim.setTarget(iv_hourglass);
		anim.start();
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
//					Toast.makeText(getApplicationContext(), "<",
//							Toast.LENGTH_SHORT).show();
				} else {
//					Toast.makeText(getApplicationContext(), ">",
//							Toast.LENGTH_SHORT).show();
				}
				return true;

			} else if (Math.abs(dX) < SWIPE_MAX_OFF_PATH
					&& Math.abs(velocityY) >= SWIPE_THRESHOLD_VELOCITY
					&& Math.abs(dY) >= SWIPE_MIN_DISTANCE) {

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
			/*
			 * int i = 0; Toast.makeText(getApplicationContext(), "Called by: "
			 * + " " + Thread.currentThread().getStackTrace()[i].getMethodName()
			 * + " i: " + i+1 + " " +
			 * Thread.currentThread().getStackTrace()[i+1].getMethodName() +
			 * " i: " + i+2 + " " +
			 * Thread.currentThread().getStackTrace()[i+2].getMethodName() +
			 * " i: " + i+3 + " " +
			 * Thread.currentThread().getStackTrace()[i+3].getMethodName() +
			 * " i: " + i+4 + " " +
			 * Thread.currentThread().getStackTrace()[i+4].getMethodName() +
			 * " i: " + i+5 + " " +
			 * Thread.currentThread().getStackTrace()[i+5].getMethodName() +
			 * " i: " + i+6 + " " +
			 * Thread.currentThread().getStackTrace()[i+6].getMethodName() +
			 * " i: " + i+7 + " " +
			 * Thread.currentThread().getStackTrace()[i+7].getMethodName() +
			 * " i: " + i+8 + " " +
			 * Thread.currentThread().getStackTrace()[i+8].getMethodName() +
			 * " i: " + i+9 + " " +
			 * Thread.currentThread().getStackTrace()[i+9].getMethodName() +
			 * " i: " + i+10 + " " +
			 * Thread.currentThread().getStackTrace()[i+10].getMethodName(),
			 * Toast.LENGTH_LONG).show();
			 */
			// Toast.makeText(getApplicationContext(), "Flags bTouch: " + bTouch
			// + " bTouchEvent: " + bTouchEvent,Toast.LENGTH_LONG).show();
			// btn_pause.setBackgroundResource(R.drawable.paused_btn);
			// v.invalidate();
			// Toast.makeText(getApplicationContext(), "Pause pressed!",
			// Toast.LENGTH_SHORT).show();
			if (isPaused) {
				onResumeTimer(remainingTimeInMilli);
			} else {
				onPauseTimer();
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
		if (frag1 != null) {
			getFragmentManager().putFragment(outState,
					PREF_LAST_SCREEN_PRACTICE, frag1);
		}
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		if (frag1 == null) {
			frag1 = getFragmentManager().getFragment(savedInstanceState,
					PREF_LAST_SCREEN_PRACTICE);

		}
	}

	private void onConfirmDialogOK(int which_title) {
		LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View customTitleView = inflater.inflate(
				R.layout.alertdialog_test_title_confirm, null);
		global_which_title = which_title;
		switch (which_title) {
		case 1:
			// already set from above

			break;
		case 2:
			customTitleView = inflater.inflate(
					R.layout.alertdialog_test_title_pause, null);
			break;
		}

		AlertDialog.Builder confirmDialog = new AlertDialog.Builder(
				TestActivity.this);
		confirmDialog.setCustomTitle(customTitleView).setItems(itemsOK,
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						if (itemsOK[which].equals(DIALOG_OK)) {
							finish();
						} else {
							onTimesUpAgain(global_which_title);
						}

					}
				});

		confirmDialog.show();
	}

	private void onTimesUpAgain(int which) {
		LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View customTitleView = inflater.inflate(
				R.layout.alertdialog_test_title_times_up, null);

		AlertDialog.Builder confirmDialog = new AlertDialog.Builder(
				TestActivity.this);
		switch (which) {
		case 1:
			confirmDialog.setCustomTitle(customTitleView).setItems(
					itemsAgainAbort, new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							if (itemsAgainAbort[which].equals(DIALOG_OK)) {

							} else if (itemsAgainAbort[which]
									.equals(DIALOG_CONTINUE)) {
								onResumeTimer(defaultTimerInMilli);
							} else if (itemsAgainAbort[which]
									.equals(DIALOG_SUBMIT)) {
								
								onPauseTimer();
								onSubmitAnswers();
							}
							return;
						}
					});
			break;
		case 2:
			confirmDialog.setCustomTitle(customTitleView).setItems(
					itemsAgainPause, new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							if (itemsAgainPause[which].equals(DIALOG_OK)) {

							} else if (itemsAgainAbort[which]
									.equals(DIALOG_CONTINUE)) {
								onResumeTimer(defaultTimerInMilli);
							}
							return;
						}
					});
			break;
		}

		confirmDialog.show();
	}

	private void setPreference(String prefname, String prefvalue) {
		preferences = PreferenceManager
				.getDefaultSharedPreferences(TestActivity.this);
		editor = preferences.edit();
		editor.putString(prefname, prefvalue);
		editor.commit();
	}

	private void setPreference(String prefname, boolean b) {
		preferences = PreferenceManager
				.getDefaultSharedPreferences(TestActivity.this);
		editor = preferences.edit();
		editor.putBoolean(prefname, b);
		editor.commit();

	}

	private void onPauseTimer() {
		if (isStopTimer) {
			try {
				myTimer.cancel();
			} catch (Exception e) {
				// TODO: handle exception
			}
			isStopTimer = false;
			return;
		}
		isPaused = !isPaused;
		// Toast.makeText(getApplicationContext(),
		// "Time used: " + timeUsedInMilli / 1000, Toast.LENGTH_SHORT)
		// .show();
		// Toast.makeText(getApplicationContext(),
		// "Time left: " + (int) ((timeInMilli - timeUsedInMilli) / 1000),
		// Toast.LENGTH_SHORT)
		// .show();

		if (isPaused) {
			try {
				myTimer.cancel();
			} catch (Exception e) {
				// TODO: handle exception
			}
			setPreference(PREF_PAUSED, true);
			anim.cancel();
			iv_pause.setVisibility(View.VISIBLE);
		} else {
			setPreference(PREF_PAUSED, false);
			iv_pause.setVisibility(View.GONE);
		}
	}

	private void onResumeTimer(long time_InMilli) {
		if (time_InMilli == 0) {
			time_InMilli = defaultTimerInMilli;
			iContinueCtr++;
			iv_pause.setVisibility(View.GONE);
		} else {

			iv_pause.setVisibility(View.GONE);
		}
		onCountDown();
		isPaused = false;

		final long time_InMillis = time_InMilli;

		this.myTimer = new CountDownTimer(time_InMillis, 1000) {

			public void onTick(long millisUntilFinished) {
				int seconds = (int) (millisUntilFinished / 1000);

				remainingTimeInMilli = millisUntilFinished;
				// Toast.makeText(getApplicationContext(),
				// "remainingTimeInMilli: " + remainingTimeInMilli / 1000,
				// Toast.LENGTH_SHORT).show();

				tv_clock.setText("" + (seconds / 3600) + ":"
						+ ((seconds % 3600) / 60) + ":"
						+ ((seconds % 3600) % 60));
				if (seconds < 11) {
					// Log.e("Clock: ", "  seconds: " + seconds);
					tv_clock.setTextColor(getResources().getColor(
							R.color.yellow));
				}
			}

			public void onFinish() {
				tv_clock.setText("00:00 !");
				anim.cancel();

				LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				View customTitleView = inflater.inflate(
						R.layout.alertdialog_test_title_times_up, null);

				AlertDialog.Builder timesUpDialog = new AlertDialog.Builder(
						TestActivity.this);
				timesUpDialog.setCustomTitle(customTitleView)
						.setTitle("Time\'s Up !!!")
						.setItems(items, new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								if (items[which].equals(DIALOG_ABORT)) {
									isStopTimer = true;
									onPauseTimer();
									onConfirmDialogOK(DIALOG_TITLE_CONFIRM);
								} else if (items[which].equals(DIALOG_PAUSE)) {
									onPauseTimer();
									setPreference(PREF_PAUSED, true);
									onConfirmDialogOK(DIALOG_TITLE_PAUSE);
								} else if (items[which].equals(DIALOG_CONTINUE)) {
									onResumeTimer(defaultTimerInMilli);
								} else if (items[which].equals(DIALOG_SUBMIT)) {
									
									onPauseTimer();
									onSubmitAnswers();
								}
								return;
							}

						});

				timesUpDialog.show();
			}
		}.start();
		// Toast.makeText(getApplicationContext(),
		// "Timer Restarted with: " + (int) (time_InMillis / 1000),
		// Toast.LENGTH_SHORT)
		// .show();

	}

	private void onClearAnswers() {
		ContentResolver cr = getContentResolver();
		cv = new ContentValues();
		cv.put(HLdb.COL_LAST_ANSWER, 0);
		cv.put(HLdb.COL_CORRECT, 0);
		cv.put(HLdb.COL_INCORRECT, 0);
//		cv.put(HLdb.COL_NO_QUESTIONS, TotalNoOfRows);
//		cv.put(HLdb.COL_PERCENT_COMPLETE, iPercentComplete);
//		cv.put(HLdb.COL_NO_MILLISECONDS, (iContinueCtr * defaultTimerInMilli)
//				+ (defaultTimerInMilli - remainingTimeInMilli));
//		String[] selectionArg1 = new String[] { test_id.toString() };

		int updatedRowCount = 0;
		// TODO Re-enable update
		updatedRowCount = cr.update(
		/* URI */HLProvider.CONTENT_URI,
		/* content values */cv,
		/* where */null,
		/* arguments */null);
	}

	private void onSubmitAnswers() {

		onSubmitDialog();
		onClearAnswers();
		remainingTimeInMilli = defaultTimerInMilli;
		// finish();
	}

	public void onSubmitDialog() {
		LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View customTitleView = inflater.inflate(
				R.layout.alertdialog_test_title_submit, null);
		final ProgressDialog progressDialog = new ProgressDialog(
				TestActivity.this);
		progressDialog.setCustomTitle(customTitleView);
		progressDialog.setCancelable(true);
		progressDialog.setMessage("Saving you Test ...");
		progressDialog.setTitle("Please Wait");
		progressDialog.setIcon(R.drawable.hl_small);
		progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		progressDialog.setProgress(0);
		progressDialog.setMax(100);

		progressDialog.show();
		onAddTest();
		progressDialog.dismiss();
		/*
		 * new Thread(new Runnable() {
		 * 
		 * @Override public void run() { // TODO Auto-generated method stub
		 * 
		 * progressStatus = performTask();
		 * 
		 * View myHandler = null; /* Dismiss the Progress bar
		 */
		/*
		 * myHandler.post(new Runnable() {
		 * 
		 * private int myProgress;
		 * 
		 * @Override public void run() { // TODO Auto-generated method stub
		 * progressDialog.dismiss();// dismiss the dialog
		 * Toast.makeText(getBaseContext(), "Task Completed",
		 * Toast.LENGTH_LONG).show(); progressStatus = 0;
		 * 
		 * myProgress = 0;
		 * 
		 * } });
		 * 
		 * }
		 * 
		 * /* Do some task
		 */
		/*
		 * private int performTask() { // try { // ---simulate doing some
		 * work--- // Thread.sleep(1000); // } catch (InterruptedException e) //
		 * { // e.printStackTrace(); // } ContentResolver cr =
		 * getContentResolver(); cv.put(HLdb.COL_NO_QUESTIONS, 1);
		 * cv.put(HLdb.COL_NO_MILLISECONDS, 1);
		 * cv.put(HLdb.COL_PERCENT_COMPLETE, 50);
		 * 
		 * cr.insert(HLProvider.CONTENT_URI, cv);
		 * 
		 * return 0; // was 0 } }).start();
		 */
	}

	Integer test_id = -1;

	private void onAddTest() {
		ContentResolver cr = getContentResolver();
		test_id = -1;

		// add the tests row

		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		Date date = new Date();

		cv = new ContentValues();
		cv.put(HLdb.COL_TEST_DATE, dateFormat.format(date));
		cv.put(HLdb.COL_EXAM_DATE, dateFormat.format(date));
		cv.put(HLdb.COL_NO_QUESTIONS, 1);
		cv.put(HLdb.COL_NO_MILLISECONDS, 1);
		cv.put(HLdb.COL_PERCENT_COMPLETE, 50);

		Uri test_id_uri = cr.insert(HLProvider.CONTENT_URI_TESTS, cv);

		test_id = Integer.parseInt(test_id_uri.getPathSegments().get(1));
//		Toast.makeText(getBaseContext(), "Test ID is : " + test_id,
//				Toast.LENGTH_LONG).show();

		// Retrieve the questions answered during the test

		cv = null;
		String[] projection1 = new String[] { HLdb.COL_ID, HLdb.COL_ANSWER_KEY,
				HLdb.COL_LAST_ANSWER, HLdb.KEY_ROWID };

		Cursor query = cr.query(HLProvider.CONTENT_URI, projection1, null,
				null, null);

		int noOfRows = 0;
		if (query.getCount() > 0) {
			noOfRows = query.getCount();
//			Toast.makeText(getApplicationContext(),
//					"Database rows " + noOfRows + " rows.", Toast.LENGTH_SHORT)
//					.show();

		} else {
			query.close();
//			Toast.makeText(getApplicationContext(), "Database has no rows.",
//					Toast.LENGTH_SHORT).show();
		}

		// Write the results to test_results

		int iCountQuestions = 0;
		int iCorrect = 0;
		int iInCorrect = 0;
		query.moveToFirst();
		while (query.isAfterLast() == false) {
			int iQuestionID = query.getInt(query
					.getColumnIndexOrThrow(HLdb.KEY_ROWID));
			int iAnswerKey = query.getInt(query
					.getColumnIndexOrThrow(HLdb.COL_ANSWER_KEY));
			int iLastAnswer = Integer.parseInt(query.getString(
					query.getColumnIndexOrThrow(HLdb.COL_LAST_ANSWER))
					.toString());

			cv = new ContentValues();

			if (test_id > -1) {
				cv.put(HLdb.COL_ID, test_id);
				cv.put(HLdb.COL_QUESTION_ID, iQuestionID);
				if (iAnswerKey == iLastAnswer) {
					cv.put(HLdb.COL_CORRECT, 1);
					cv.put(HLdb.COL_INCORRECT, 0);
					iCorrect++;
				} else {
					cv.put(HLdb.COL_CORRECT, 0);
					cv.put(HLdb.COL_INCORRECT, 1);
					iInCorrect++;
				}

				Uri insert_id_uri = cr.insert(
						HLProvider.CONTENT_URI_TEST_RESULTS, cv);
				if (Integer.parseInt(insert_id_uri.getPathSegments().get(1)) == 0) {
					throw new SQLException("Failed to insert row into "
							+ insert_id_uri);
				} else {
					iCountQuestions++;
				}

				// Toast.makeText(getApplicationContext(), "insert test: " +
				// test_id + " q id: " + iQuestionID,
				// Toast.LENGTH_SHORT).show();

			}
			query.moveToNext();
		}
		int TotalNoOfRows = noOfRows;
		query.close();
		int iPercentComplete = 0;
		try {
			iPercentComplete = ((int) TotalNoOfRows / (iCorrect + iInCorrect)) * 100;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		cv = new ContentValues();
		cv.put(HLdb.COL_NO_CORRECT, iCorrect);
		cv.put(HLdb.COL_NO_INCORRECT, iInCorrect);
		cv.put(HLdb.COL_NO_QUESTIONS, TotalNoOfRows);
		cv.put(HLdb.COL_PERCENT_COMPLETE, iPercentComplete);
		cv.put(HLdb.COL_NO_MILLISECONDS, (iContinueCtr * defaultTimerInMilli)
				+ (defaultTimerInMilli - remainingTimeInMilli));
		String[] selectionArg1 = new String[] { test_id.toString() };

		int updatedRowCount = 0;
		// TODO Re-enable update
		updatedRowCount = cr.update(
		/* URI */HLProvider.CONTENT_URI_TESTS,
		/* content values */cv,
		/* where */HLdb.KEY_ROWID + " = ? ",
		/* arguments */selectionArg1);
		
		int seconds = (int) ( ((iContinueCtr * defaultTimerInMilli)
				+ remainingTimeInMilli) / 1000);



//		"" + (seconds / 3600) + ":" + ((seconds % 3600) / 60) + ":" + ((seconds % 3600) % 60)
		
		
		if(iCorrect == 0) {
		Toast.makeText(getApplicationContext(), "Test # " + test_id.toString() + " saved.  " + TotalNoOfRows + " questions answered. ",
				Toast.LENGTH_LONG).show();
		} else {
			Toast.makeText(getApplicationContext(), "Test # " + test_id.toString() + " saved.  " + iCorrect + " answered correctly. ",
					Toast.LENGTH_LONG).show();
		}
		Bundle b = null;
		Intent intent;
		  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
			  View v = ((ViewGroup)findViewById(android.R.id.content)).getChildAt(0);
              b = ActivityOptions.makeScaleUpAnimation(v, 0, 0, v.getWidth()/2, v.getHeight()/2).toBundle();
          }

		  	setPreference(PREF_LAST_TEST_ID, test_id.toString());
			setPreference(PREF_LAST_SCREEN, PREF_LAST_SCREEN_TEST);
			intent = new Intent(TestActivity.this, PostTestActivity.class);
		//	intent.putExtra(PREF_LAST_SCREEN, prefLastScreen);
			startActivity(intent, b);
		

	}

	@Override
	public void onBackPressed() {
		if (iBackCtr > 1) {
			super.onBackPressed();
			onClearAnswers();
			Intent i = new Intent(TestActivity.this, PreTestActivity.class);
			i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(i);
			this.finish();
		} else if (isPaused) {
			iBackCtr++;
			onPauseTimer();
			setPreference(PREF_PAUSED, true);
			onConfirmDialogOK(DIALOG_TITLE_PAUSE);
//			super.onBackPressed();
		} else {
			iBackCtr++;
			onPauseTimer();
			this.myTimer.onFinish();
//			super.onBackPressed();
		}

	}

	public void onAnswerSelected(int key, int value, boolean isInitialized) {
		// TODO Auto-generated method stub
//		Toast.makeText(getApplicationContext(), "TF Act Key: " + key + " Row:  " + value + " isInitialize: " + isInitialized,
//				Toast.LENGTH_LONG).show();
		
	   	 map.put(key, value);
	   	 mapInitialized.put(key, isInitialized);
	}

   public int getAnswerSelected(int key) {
	   if(map.get(key) == null) {
		   map.put(key, 0);
	   }
//	   Toast.makeText(getApplicationContext(), "Return SELECTED Key: " + key + " return:  " + map.get(key),
//				Toast.LENGTH_LONG).show();
	   return map.get(key);
   }

   public boolean getAnswerState(int key) {
	   if(mapInitialized.get(key) == null) {
		   mapInitialized.put(key, false);
	   }
//	   Toast.makeText(getApplicationContext(), "Return STATE Key: " + key + " return:  " + mapInitialized.get(key),
//				Toast.LENGTH_LONG).show();
	   return mapInitialized.get(key);
   }
	public void onDisplayAnswerTest(int key, int iLastAnswer, String literal) {
		// TODO Test - remove after correct
		Toast.makeText(getApplicationContext(), literal + key + " iLastAnswer:  " + iLastAnswer + " map.get(key): " + map.get(key) + " mapInitialized.get(key): " + mapInitialized.get(key),
				Toast.LENGTH_LONG).show();
		Log.v("TestActivity", literal + key + " iLastAnswer:  " + iLastAnswer + " map.get(key): " + map.get(key) + " mapInitialized.get(key): " + mapInitialized.get(key));
	}

	@Override
	public void onSubmitSelected() {
		// TODO Auto-generated method stub
		this.myTimer.onFinish();
	}

}
